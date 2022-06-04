package com.heima.travel.service;

import com.heima.travel.vo.ResultInfo;

public interface FavoriteService {
    ResultInfo isFavoriteByRid(Integer rid);


    ResultInfo addFavorite(Integer rid);

}
