package com.heima.travel.service;

import com.heima.travel.pojo.User;
import com.heima.travel.vo.ResultInfo;



public interface UserService {
    //注册时对验证码进行验证的逻辑接口
    ResultInfo registerUser(User user, String sessionCheckCode, String check);
}
