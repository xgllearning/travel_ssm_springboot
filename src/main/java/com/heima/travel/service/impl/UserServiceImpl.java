package com.heima.travel.service.impl;

import com.heima.travel.mapper.UserMapper;
import com.heima.travel.pojo.User;
import com.heima.travel.service.UserService;

import com.heima.travel.utils.Md5Util;
import com.heima.travel.utils.UuidUtil;
import com.heima.travel.vo.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;








/**
 * @author laofang
 * @description
 * @date 2021-06-19
 */
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

        //5.发送邮箱TODO：

        return new ResultInfo(true, null, null);
    }
}