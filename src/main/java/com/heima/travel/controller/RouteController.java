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

}