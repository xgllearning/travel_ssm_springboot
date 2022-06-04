package com.heima.travel.service.impl;

import com.heima.travel.exception.BusinessException;
import com.heima.travel.mapper.UserMapper;
import com.heima.travel.pojo.User;
import com.heima.travel.service.UserService;

import com.heima.travel.utils.MailUtil;
import com.heima.travel.utils.Md5Util;
import com.heima.travel.utils.UuidUtil;
import com.heima.travel.vo.Code;
import com.heima.travel.vo.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@Service
//添加事务，事务正常应该放在service接口层，可以给实现类每个方法进行事务管理
/**声明式事务
 1.  SpringConfig类添加 @EnableTransactionManagement
 -> 开启声明式事务
 2. SpringConfig类 声明 DataSourceTransactionManager
 3. 在业务方法上 添加注解 @Transactional
 4. 事务定义的4个属性
 1). isolation
 一般默认即可
 2). readOnly
 增删改设置false(默认值),读设置true
 3). timeout
 默认为-1不超时,一般都要设置,单位秒
 4). propagation (传播行为)增删改：REQUIRED (默认值)，只是查询：SUPPORT
 **/
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    //因为这个方法中既有插入又有查询，因此重新设置事务，将传播行为改为Propagation.REQUIRED，并可写
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    //验证码验证逻辑
    public ResultInfo registerUser(User user, String sessionCheckCode, String check) {
        //1.验证注册码与session下的注册码是否一致
        if (check == null || !check.equalsIgnoreCase(sessionCheckCode)) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码有误");
            return resultInfo;//结束，返回错误信息
        }
        //2.如果验证码和输入的验证码一致，则查询当前用户是否已经注册，此时需要调用dao层，所以自动装配UserMapper，根据前台传过来的用户名进行查询，查询后返回一个用户信息
        User dbUser =
                this.userMapper.findUserByUserName(user.getUsername());

        if (dbUser != null) {
            //如果从数据库查询到该用户名信息，说明已经注册过了
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名已经注册");
            return resultInfo;
        }

        //3.否则说明没有注册过该用户，进行添加用户
        //3.1 需要将传入的用户密码进行加密处理，通过MD5工具类进行处理
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        //3.2 设置用户状态为未激活，只有注册完再验证完邮箱后才算激活成功,否则数据库用户状态为N
        user.setStatus("N");
        //3.3 设置激活用户的激活码，通过UUID获得一个随机码，充当激活码
        String activeCode = UuidUtil.getUuid();
        user.setCode(activeCode);
        //4.往数据库插入用户信息，通过dao层进行处理
        this.userMapper.insertUser(user);

        //5.发送邮箱
        //5.1 设置正文,到时点击一下会通过get拼接字符串形式将激活码携带给后台，/user/active方法？code参数
        String content="<a href='http://localhost:8080/user/active?code="+activeCode+"'>点击激活</a>";
        //5.2 发送邮箱
        MailUtil.sendMail(user.getEmail(),content);
        return new ResultInfo(true,null,null);
    }
    @Override
    public void activeUser(String code) throws IOException {
        //1.激活用户,本质其实是更新status，返回影响行数
        Integer count= this.userMapper.activeUser(code);
        if (count>0) {
            //如果受影响行数大于0，说明激活成功。此时需要资源跳转login.html页面下(一般情况下，是response.redirect重定向到)
            //此时因为没有传过来参数response，使用RequestContextHolder获取，该参数可以获取response\request\session等各种域，功能强大
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
            response.sendRedirect("/login.html");
        }else{
            //抛出异常，激活失败，交给全局异常处理
            throw new BusinessException(Code.BUSINESS_ERR,"激活码不对");
        }
    }
    @Override
    public ResultInfo login(String username, String password, String check) {
        //TODO：通过该工具类RequestContextHolder获取session中的数据，需要先强转为ServletRequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //获取当前会话的session，根据名字CHECK_CODE取出session中存放的随机验证码，与用户输入的验证码check进行验证
        HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
        String sessionCheckCode = (String)session.getAttribute("CHECK_CODE");
        if (check==null || ! check.equalsIgnoreCase(sessionCheckCode)) {
            return new ResultInfo(false,"验证码错误");
        }
        //根据用户和密码查询用户是否存在，注意：需要将用户的密码进行MD5加密，否则无法与数据库中的密码进行匹配
        User dbUser= this.userMapper.findUserByUserNameAndPassword(username,Md5Util.encodeByMd5(password));
        if (dbUser==null) {
            return new ResultInfo(false,"用户名或者密码错误");
        }
        //如果用户存在，则将用户的信息保存到session下，登录后可以后续使用当前该用户信息
        session.setAttribute("CUR_USER",dbUser);
        return new ResultInfo(true,null,null);
    }

    @Override
    public ResultInfo getLoginUserData() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //获取当前会话的session
        HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
        //获取当前用户信息
        User curUser = (User) session.getAttribute("CUR_USER");
        if (curUser==null) {
            return new ResultInfo(false,"当前用户未登录");
        }
        return new ResultInfo(true,curUser,null);
    }

    @Override
    public void loginOut() throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes1 = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = requestAttributes1.getRequest();
        HttpServletResponse response = requestAttributes1.getResponse();
        HttpSession session = request.getSession();
        //销毁session中存储的用户信息
        session.removeAttribute("CUR_USER");
        //退出后退出原来页面
        String preUrl = request.getHeader("referer");
        response.sendRedirect(preUrl);

    }
}