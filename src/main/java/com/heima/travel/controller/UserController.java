package com.heima.travel.controller;

import com.heima.travel.pojo.User;
import com.heima.travel.service.UserService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author laofang
 * @description
 * @date 2021-06-19
 */
//注意：@RestController=requestBody+controller
@RestController
//异步请求，/user/register
@RequestMapping("/user")
public class UserController {

    @Autowired
    //创建一个service层接口，并创建实现类实现该接口
    private UserService userService;

    //post携带表单数据 /register
    //处理用户注册请求
    @PostMapping("/register")
    //如果前台传递数据不是是json而是get的url参数,属性名：值的方式，只要属性名(或对象里面的属性名)与前台的属性名一致，就会自动填充赋值
    //⭐如果前台传过来的是json(post请求一般为json)，则需要在参数中@requestBody，会自动填充还需要获取session，session中保存了随机验证码，check是用户输入的验证码
    public ResultInfo register(User user, String check, HttpSession session) {
        //1.获取session中保存的验证码，根据ValidateController中填入的name获取值
        String sessionCheckCode = (String) session.getAttribute("CHECK_CODE");
        //2.调用服务层方法registerUser(注册方法)，获取响应结果,不写this也可以
        ResultInfo result = this.userService.registerUser(user, sessionCheckCode, check);
        //返回结果，如果session中验证码和用户输入的验证码一致则成功注册，并将结果返回给前台
        return result;
    }
    //不建议将web层的api引入service层的方法入参中
    //    @GetMapping("/active")
    //    public void activeUser(String code,HttpServletResponse response){
    //        this.userService.activeUser(code, response );
    //    }

    /**
     * 激活用户
     * @param code
     * @throws IOException
     */
    @GetMapping("/active")
    public void activeUser(String code) throws IOException {
        //点击验证邮件后，code与携带的code名一样会自动赋值，然后调用service层activeUser方法进行code对比，跟数据库中的code一致后会更新status，由N->Y
        this.userService.activeUser(code);
    }
    /**
     * 登录功能，根据用户名密码验证码进行验证登录
     * @param username
     * @param password
     * @param check
     * @return
     */
    @PostMapping("/login")
    public ResultInfo login(String username,String password,String check){
        return this.userService.login(username,password,check);
    }

    /**
     处理用户登录信息的查询，回显登录用户的信息
     */
    @PostMapping("/getLoginUserData")
    public ResultInfo getLoginUserData(){
        return  this.userService.getLoginUserData();
    }
}
