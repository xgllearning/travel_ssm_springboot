package com.heima.travel.mapper;

import com.heima.travel.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //验证码一致后，查询该用户是否已经注册，建议打注解@Param("name")，在xml传值时，根据@Param("name")即可
    User findUserByUserName(@Param("name") String username);
    //如果该用户未注册，向数据库插入用户数据
    void insertUser(User user);

    Integer activeUser(@Param("code") String code);
    //TODO：两个参数必须写注解，否则识别不了
    User findUserByUserNameAndPassword(@Param("name") String username,@Param("password") String encodeByMd5);
}
