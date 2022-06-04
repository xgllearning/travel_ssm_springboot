package com.heima.travel.service;

import com.heima.travel.vo.ResultInfo;

public interface RouteService {

    ResultInfo routeCareChoose();

    ResultInfo findRouteList(Integer cid, Integer curPage, String rname);

    ResultInfo findRouteInfoByRid(Integer rid);

    ResultInfo findRoutesFavoriteRank(Integer curPage, String rname, Double startPrice, Double endPrice);

}
