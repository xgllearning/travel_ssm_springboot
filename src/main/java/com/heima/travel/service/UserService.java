package com.heima.travel.service;

import com.heima.travel.pojo.User;
import com.heima.travel.vo.ResultInfo;

import java.io.IOException;


public interface UserService {
    //注册时对验证码进行验证的逻辑接口
    ResultInfo registerUser(User user, String sessionCheckCode, String check);
    //创建userService.activeUser方法
    void activeUser(String code) throws IOException;

    ResultInfo login(String username, String password, String check);

}
