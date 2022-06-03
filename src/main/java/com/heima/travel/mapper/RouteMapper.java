package com.heima.travel.mapper;

import com.heima.travel.pojo.Route;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {

    List<Route> popularityRoutes();

    List<Route> newRoutes();

    List<Route> themesRoutes();
    //多个参数必须param起别名
    List<Route> findRouteList(@Param("cid") Integer cid, @Param("rname") String rname);

    Route findRouteInfoByRid(Integer rid);
}
