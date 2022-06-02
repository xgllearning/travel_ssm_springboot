package com.heima.travel.controller;

import com.heima.travel.pojo.User;
import com.heima.travel.service.UserService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;


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
}
