package com.heima.travel.mapper;

import com.heima.travel.pojo.Route;

import java.util.List;

public interface RouteMapper {
    List<Route> popularityRoutes();

    List<Route> newRoutes();

    List<Route> themesRoutes();
}
