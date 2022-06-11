package com.heima.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.travel.pojo.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper extends BaseMapper<Favorite> {
    /**
     * 多表查询保留，通过xml实现
     * @param uid
     * @return
     */
    List<Favorite> findFavorites(Page<Favorite> page,@Param("uid") int uid);
}
