package com.heima.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.travel.exception.SystemException;
import com.heima.travel.mapper.RouteMapper;
import com.heima.travel.pojo.Route;
import com.heima.travel.service.RouteService;
import com.heima.travel.vo.Code;
import com.heima.travel.vo.PageBean;
import com.heima.travel.vo.ResultInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class RouteServiceImpl extends ServiceImpl<RouteMapper,Route> implements RouteService {

    @Override
    public ResultInfo routeCareChoose() {
        //1.获取人气前四,通过分页实现，取第一页，每页四条，mapper是通过order进行排序
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
//        PageHelper.startPage(1, 4);
//        List<Route> popularityRoutes = this.routeMapper.popularityRoutes();
        Page<Route> topPage = new Page<>(1,4);
        LambdaQueryWrapper<Route> topWrapper = Wrappers.lambdaQuery(Route.class);
        topWrapper.orderByDesc(Route::getCount);
        this.page(topPage,topWrapper);
        List<Route> popularityRoutes = topPage.getRecords();

        //2.获取最新的旅游路线前4
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
//        PageHelper.startPage(1, 4);
//        List<Route> newRoutes = this.routeMapper.newRoutes();
        Page<Route> newPage = new Page<>(1,4);
        LambdaQueryWrapper<Route> newWrapper = Wrappers.lambdaQuery(Route.class);
        newWrapper.orderByDesc(Route::getRdate);
        this.page(newPage,newWrapper);
        List<Route> newRoutes = newPage.getRecords();
        //3.获取主题路由线路前四
        //PageHelper.startPage会自动在mapper的sql语句后面自动追加limit分页sql语句
//        PageHelper.startPage(1, 4);
//        List<Route> themesRoutes = this.routeMapper.themesRoutes();
        Page<Route> themesPage = new Page<>(1,4);
        LambdaQueryWrapper<Route> themesWrapper = Wrappers.lambdaQuery(Route.class);
        themesWrapper.eq(Route::getIsThemeTour,"1").orderByDesc(Route::getRdate);
        this.page(themesPage,themesWrapper);
        List<Route> themesRoutes = themesPage.getRecords();
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
//        PageHelper.startPage(curPage,8);
        //分类cid,rname模糊查询
//        List<Route> routes= this.routeMapper.findRouteList(cid,rname);
        //查询到的List，对其进行处理，使用com.github.pagehelper包装得到PageInfo对象；
        //new一个PageInfo-API，传入list集合可以实现包装
//        PageInfo<Route> pageInfo = new PageInfo<>(routes);
        Page<Route> page = new Page<>(curPage,8);
        LambdaQueryWrapper<Route> wrapper = Wrappers.lambdaQuery(Route.class);
        wrapper.eq(Route::getCid,cid)
                .like(Route::getRname,rname);
        this.page(page,wrapper);
        List<Route> routes= page.getRecords();
        //因为pageInfo的属性可以自动填充，所以与前台所需要的数据对象不太一致，前台需要的是pageBean对象，所以需要创建一个pageBean并设置pageBean中的各个参数
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalPage(page.getPages());//总页数
        pageBean.setPageSize(page.getSize());//每页多少条
        //获取上一页
        Integer prePage=curPage-1>0?curPage-1:1;
        //获取下一页
        //获取总页数
        Integer totalPage=Long.valueOf(page.getPages()).intValue() ;
        Integer nextPage=(curPage+1> totalPage)?totalPage:curPage+1;
        pageBean.setPrePage(prePage);//上一页
        pageBean.setNextPage(nextPage);//下一页
        pageBean.setCurPage(curPage);//当前页
        pageBean.setData(routes);//当前页列表
        pageBean.setCount(page.getTotal());//总记录数
        return new ResultInfo(true,pageBean,null);
    }

    @Override
    public ResultInfo findRouteInfoByRid(Integer rid) {
        //1.根据旅游路线id查询路线信息
        Route route= this.baseMapper.findRouteInfoByRid(rid);
        if (null==route) {
            //抛异常，抛一个运行时异常交给异常管理器统一处理，异常均在controller层进行处理
            throw new SystemException(Code.SYSTEM_ERR,"没有查询到该路线信息");

        }
        return new ResultInfo(true,route,null);
    }


    @Override
    public ResultInfo findRoutesFavoriteRank(Integer curPage, String rname, Double startPrice, Double endPrice) {
        //使用分页插件实现分页
        Page<Route> page = new Page<>(curPage,8);
        LambdaQueryWrapper<Route> wrapper = Wrappers.lambdaQuery(Route.class);
        wrapper.like(Route::getRname,rname).ge(startPrice!=null,Route::getPrice,startPrice)
                .le(endPrice!=null,Route::getPrice,endPrice);
        wrapper.orderByDesc(Route::getCount);
        this.page(page,wrapper);
        List<Route> routes= page.getRecords();
        if (CollectionUtils.isEmpty(routes)) {
            return new ResultInfo(false,"查询不到列表信息");
        }
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalPage(page.getPages());
        pageBean.setPageSize(page.getSize());
        //获取上一页
        Integer prePage=curPage-1>0?curPage-1:1;
        //获取下一页
        //获取总页数
        Integer totalPage=Long.valueOf(page.getPages()).intValue() ;
        Integer nextPage=(curPage+1> totalPage)?totalPage:curPage+1;
        pageBean.setPrePage(prePage);
        pageBean.setNextPage(nextPage);
        pageBean.setCurPage(curPage);
        pageBean.setData(routes);
        pageBean.setCount(page.getTotal());
        return new ResultInfo(true,pageBean,null);
    }
}
