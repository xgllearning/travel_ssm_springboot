package com.heima.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.travel.pojo.Route;
import com.heima.travel.vo.ResultInfo;

public interface RouteService extends IService<Route> {

    ResultInfo routeCareChoose();

    ResultInfo findRouteList(Integer cid, Integer curPage, String rname);

    ResultInfo findRouteInfoByRid(Integer rid);

    ResultInfo findRoutesFavoriteRank(Integer curPage, String rname, Double startPrice, Double endPrice);

}
