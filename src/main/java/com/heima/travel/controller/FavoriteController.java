package com.heima.travel.controller;

import com.heima.travel.service.FavoriteService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;


    @PostMapping("/isFavoriteByRid")
    public ResultInfo isFavoriteByRid(Integer rid) {
        return this.favoriteService.isFavoriteByRid(rid);
    }
    /**
     * 添加收藏
     * @param rid
     * @return
     */
    @PostMapping("/addFavorite")
    public ResultInfo addFavorite(Integer rid){
        return  this.favoriteService.addFavorite(rid);
    }

    /**
     * 分页查询我的收藏
     * @param curPage
     * @return
     */
    @PostMapping("/findFavoriteByPage")
    public ResultInfo findFavoriteByPage(
            @RequestParam(value = "curPage",required = false,defaultValue = "1") Integer curPage){
        return this.favoriteService.findFavoriteByPage(curPage);
    }
}
