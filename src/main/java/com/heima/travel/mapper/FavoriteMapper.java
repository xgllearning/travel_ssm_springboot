package com.heima.travel.mapper;

import com.heima.travel.pojo.Favorite;
import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    Favorite isFavoriteByRid(@Param("uid") int uid, @Param("rid") Integer rid);

    Integer countFavorite(@Param("uid") int uid, @Param("rid") Integer rid);
}
