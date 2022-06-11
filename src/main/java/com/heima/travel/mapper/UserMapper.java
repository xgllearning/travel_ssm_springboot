package com.heima.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.travel.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

}
