package com.heima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.travel.mapper.RouteMapper;
import com.heima.travel.pojo.Route;
import com.heima.travel.service.RouteService;
import com.heima.travel.vo.PageBean;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Override
    public ResultInfo findRouteList(Integer cid, Integer curPage, String rname) {
        //会使用到分页技术，因此使用分页api-PageHelper参数一：当前页码，从哪一页开始显示，参数二每页显示条数
        PageHelper.startPage(curPage,8);
        //分类cid,rname模糊查询
        List<Route> routes= this.routeMapper.findRouteList(cid,rname);
        //查询到的List，对其进行处理，使用com.github.pagehelper包装得到PageInfo对象；
        //new一个PageInfo-API，传入list集合可以实现包装
        PageInfo<Route> pageInfo = new PageInfo<>(routes);
        //因为pageInfo的属性可以自动填充，所以与前台所需要的数据对象不太一致，前台需要的是pageBean对象，所以需要创建一个pageBean并设置pageBean中的各个参数
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalPage(pageInfo.getPages());//总页数
        pageBean.setPageSize(pageInfo.getPageSize());//每页多少条
        pageBean.setPrePage(pageInfo.getPrePage());//上一页
        pageBean.setNextPage(pageInfo.getNextPage());//下一页
        pageBean.setCurPage(pageInfo.getPageNum());//当前页
        pageBean.setData(routes);//当前页列表
        pageBean.setCount(pageInfo.getTotal());//总记录数
        return new ResultInfo(true,pageBean,null);
    }

    @Override
    public ResultInfo findRouteInfoByRid(Integer rid) {
        //1.根据旅游路线id查询路线信息
        Route route= this.routeMapper.findRouteInfoByRid(rid);
        if (null==route) {
            //抛异常，抛一个运行时异常交给异常管理器统一处理，异常均在controller层进行处理
            throw new RuntimeException(rid+"对应的旅游路线不存在");
        }
        return new ResultInfo(true,route,null);
    }


    @Override
    public ResultInfo findRoutesFavoriteRank(Integer curPage, String rname, Double startPrice, Double endPrice) {
        //使用分页插件实现分页，当前页和每页显示条数
        PageHelper.startPage(curPage,8);
        //根据名、最低价格、最高价格向数据库模糊查询
        List<Route> routes= this.routeMapper.findRoutesFavoriteRank(rname,startPrice,endPrice);
        if (CollectionUtils.isEmpty(routes)) {
            //判断查询出来集合是否为空
            return new ResultInfo(false,"查询不到列表信息");
        }
        //插件生成pageInfo信息，传入查出的集合
        PageInfo<Route> pageInfo = new PageInfo<>(routes);
        //返回前台pageBean对象，封装好数据
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalPage(pageInfo.getPages());
        pageBean.setPageSize(pageInfo.getPageSize());
        pageBean.setPrePage(pageInfo.getPrePage());
        pageBean.setNextPage(pageInfo.getNextPage());
        pageBean.setCurPage(pageInfo.getPageNum());
        pageBean.setData(routes);
        pageBean.setCount(pageInfo.getTotal());
        return new ResultInfo(true,pageBean,null);
    }
}
