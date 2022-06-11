package com.heima.travel.controller;

import com.heima.travel.identify.ValidateGroup;
import com.heima.travel.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/validate")
@Slf4j
public class TestValidateController {

    /**
     * 模拟注册
     * 开启入参的校验 @Valid
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public User addUser(@RequestBody @Valid User user, Errors errors){
        System.out.println(user);
        if (errors.hasErrors()) {
            //获取所有字段的校验错误
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                //获取字段名称
                String fieldName = fieldError.getField();
                //获取错误信息
                String errorMsg = fieldError.getDefaultMessage();
                log.info("字段：{},发生：{}",fieldName,errorMsg);
            }
        }
        return user;
    }

    /**
     * 模拟登陆
     * @param user
     * @param errors
     * @return
     */
    @PostMapping("/login")
    public User login(@RequestBody @Validated(ValidateGroup.class) User user, Errors errors){
        System.out.println(user);
        if (errors.hasErrors()) {
            //获取所有字段的校验错误
            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                //获取字段名称
                String fieldName = fieldError.getField();
                //获取错误信息
                String errorMsg = fieldError.getDefaultMessage();
                log.info("字段：{},发生：{}",fieldName,errorMsg);
            }
        }
        return user;
    }
}
