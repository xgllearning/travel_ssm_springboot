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

    void updateCount(@Param("rid") Integer rid,@Param("count") int count);

    /**
     * 根据路线的id查询当前路线收藏的数量(用户点击收藏数据库更新后的查询操作)
     * @param rid
     * @return
     */
    Integer findCountByRoutId(Integer rid);
}
