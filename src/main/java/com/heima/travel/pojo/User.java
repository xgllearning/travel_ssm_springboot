package com.heima.travel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.heima.travel.identify.ValidateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;//用户id
    @NotNull(message = "用户名为null")
    @Length(min = 6,max = 11,message = "用户名称长度有误")
    private String username;//用户名，账号
    @Length(min = 6,max = 6,message = "密码长度有误")
    private String password;//密码
    private String name;//真实姓名
    private String birthday;//出生日期
    private String sex;//男或女
    private String telephone;//手机号
    @Email(message = "邮箱格式有误",groups = {ValidateGroup.class})
    private String email;//邮箱
    private String status;//激活状态，Y代表激活，N代表未激活
    private String code;//激活码（要求唯一）

}
