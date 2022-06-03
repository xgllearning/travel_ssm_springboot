package com.heima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.heima.travel.mapper.RouteMapper;
import com.heima.travel.pojo.Route;
import com.heima.travel.service.RouteService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteMapper routeMapper;


    @Override
    public ResultInfo routeCareChoose() {
        //1.获取人气前四,通过分页实现，取第一页，每页四条，mapper是通过order进行排序
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
        PageHelper.startPage(1, 4);
        List<Route> popularityRoutes = this.routeMapper.popularityRoutes();
        //2.获取最新的旅游路线前4
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
        PageHelper.startPage(1, 4);
        List<Route> newRoutes = this.routeMapper.newRoutes();
        //3.获取主题路由线路前四
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
        PageHelper.startPage(1, 4);
        List<Route> themesRoutes = this.routeMapper.themesRoutes();
        //通过map组装前台需要的数据data,news:[],popularity:[],themes:[]
        HashMap<String, List<Route>> data = new HashMap<>();
        data.put("news", newRoutes);
        data.put("popularity", popularityRoutes);
        data.put("themes", themesRoutes);
        return new ResultInfo(true, data, null);
    }
}
