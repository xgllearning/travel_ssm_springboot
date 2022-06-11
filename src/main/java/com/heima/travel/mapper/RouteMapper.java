package com.heima.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.travel.pojo.Route;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RouteMapper extends BaseMapper<Route> {
    /**
     * 根据路线的id查询路线关联的信息，包含商家，路线分类，路线，路线图片，只保留多表查询，单表查询都通过mybatis-plus实现
     * @param rid
     * @return
     */
    Route findRouteInfoByRid(Integer rid);

}
