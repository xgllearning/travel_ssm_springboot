package com.heima.travel.mapper;

import com.heima.travel.pojo.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper {
    Favorite isFavoriteByRid(@Param("uid") int uid, @Param("rid") Integer rid);

    Integer countFavorite(@Param("uid") int uid, @Param("rid") Integer rid);

    void addFavorite( @Param("uid") int uid, @Param("rid") Integer rid);

    List<Favorite> findFavorites(int uid);
}
