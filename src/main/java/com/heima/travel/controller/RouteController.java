package com.heima.travel.controller;

import com.heima.travel.service.RouteService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    /**
     * 获取人气 主题 最新的旅游线路集合前4
     * @return
     */
    @PostMapping("/routeCareChoose")
    public ResultInfo routeCareChoose(){
        return this.routeService.routeCareChoose();
    }

    /**
     * 分页查询路线
     * @param cid
     * @param curPage
     * @param rname
     * 用户分别可以使用分类id，路线名称以及当前页信息进行模糊查询展示
     * @return
     */
    @PostMapping("/findRouteList")
    //@RequestParam(required = false,defaultValue = "1")表示非必须，若没传过来，默认当前页为1
    public ResultInfo findRouteList(
            @RequestParam(value = "cid",required = false) Integer cid,
            @RequestParam(value = "curPage",required = false,defaultValue = "1") Integer curPage,
            @RequestParam(value = "rname",required = false) String rname){
        //rname的作用是搜索框模糊搜索
        ResultInfo result=this.routeService.findRouteList(cid,curPage,rname);
        return result;
    }
    /**点击查看详情后，根据线路Rid查询路线的详细信息
     * 商家表：路线的商家等
     * 路线表：收藏次数，路线的详细介绍，价格等
     * 路线图片表：图片等
     * 路线分类表：分类等
     * @param rid*/
    @PostMapping("/findRouteByRid")
    public ResultInfo findRouteByRid(Integer rid){
        return this.routeService.findRouteInfoByRid(rid);
    }

    /**
     * 分页查询收藏排行榜
     * @param curPage
     * @param rname
     * @param startPrice
     * @param endPrice
     * @return
     */
    //TODO：前台传过来四个参数，且均为非必须，并且curPage当前页默认为第一页，
    @PostMapping("/findRoutesFavoriteRank")
    public ResultInfo findRoutesFavoriteRank(
            @RequestParam(value = "curPage",required = false,defaultValue = "1") Integer curPage,
            @RequestParam(value = "rname",required = false) String rname,
            @RequestParam(value = "startPrice",required = false)  Double startPrice,
            @RequestParam(value = "endPrice",required = false)  Double endPrice){
        return  this.routeService.findRoutesFavoriteRank(curPage,rname,startPrice,endPrice);
    }
}