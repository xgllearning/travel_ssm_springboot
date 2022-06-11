package com.heima.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.travel.pojo.Favorite;
import com.heima.travel.vo.ResultInfo;

public interface FavoriteService extends IService<Favorite> {
    ResultInfo isFavoriteByRid(Integer rid);


    ResultInfo addFavorite(Integer rid);

    ResultInfo findFavoriteByPage(Integer curPage);

}
