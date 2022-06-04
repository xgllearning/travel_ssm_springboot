package com.heima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.travel.mapper.FavoriteMapper;
import com.heima.travel.mapper.RouteMapper;
import com.heima.travel.pojo.Favorite;
import com.heima.travel.pojo.User;
import com.heima.travel.service.FavoriteService;
import com.heima.travel.vo.PageBean;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Override
    public ResultInfo isFavoriteByRid(Integer rid) {
        //1.从当前session中获取用户信息RequestContextHolder.getRequestAttributes()
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        User curUser = (User) session.getAttribute("CUR_USER");
        if (null==curUser) {
            return new ResultInfo(false,"用户未登录");
        }
        //不采取，增大数据库io的开销 浪费内存
//        Favorite favorite=this.favoriteMapper.isFavoriteByRid(curUser.getUid(),rid);
//        if (null==favorite) {
//            //用户未收藏
//            return new ResultInfo(true,false,null);
//        }
        // 把当前用户id和路线rid传过去，查询tab_favorite表中用户是否收藏该路线.如果收藏过则能查到数据，没收藏过则返回0
        Integer count =this.favoriteMapper.countFavorite(curUser.getUid(),rid);
        if (count==0) {
            //用户未收藏
            return new ResultInfo(true,false,null);
        }
        return new ResultInfo(true,true,null);
    }

    @Override
    //事务管理增删改需要Propagation.REQUIRED(默认值)，只读为false
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public ResultInfo addFavorite(Integer rid) {
        //1.获取当前的用户对象
        //1.从当前session中获取用户信息
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        User curUser = (User) session.getAttribute("CUR_USER");
        if (null==curUser) {
            return new ResultInfo(false,0,"用户未登录");
        }
        //TODO：添加收藏数据之前需要先查询，确定当前用户是否收藏了对应路线，没有收藏才会往数据库中tab_favorite表中添加rid与uid
        //因为这俩是联合主键，如果插入的数据与数据库原有数据一致会出现异常
        Integer count = favoriteMapper.countFavorite(curUser.getUid(), rid);
        if (count>0) {
            return new ResultInfo(false,"用户已收藏");
        }
        //此时说明用户未收藏该页面，所以向收藏表添加数据,向tab_favorite表中添加rid与uid，增加当前用户收藏该线路的对应关系
        this.favoriteMapper.addFavorite(curUser.getUid(),rid);
        //向收藏表添加完数据后，说明用户收藏该线路，需要更新路线的全部收藏数量，在tab_route表中的count+1，传入rid和1代表收藏次数+1
        this.routeMapper.updateCount(rid,1);
        //获取旅游路线的最新收藏数量，展示在页面
        Integer fCount=this.routeMapper.findCountByRoutId(rid);
        return new ResultInfo(true,fCount,null);
    }
    @Override
    public ResultInfo findFavoriteByPage(Integer curPage) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        User curUser = (User) session.getAttribute("CUR_USER");
        if (null==curUser) {
            return new ResultInfo(false,"用户未登录");
        }
        PageHelper.startPage(curPage,12);
        //TODO：注意此处有bug，因为数据库日期为now().所以如果日期为同一天的话，只能查询出一条数据，修改表结构即可解决
        List<Favorite> favoriteList= this.favoriteMapper.findFavorites(curUser.getUid());
        if (CollectionUtils.isEmpty(favoriteList)) {
            return new ResultInfo(false,"用户未收藏路线");
        }
        PageInfo<Favorite> pageInfo = new PageInfo<>(favoriteList);
        PageBean<Favorite> pageBean = new PageBean<>();
        pageBean.setTotalPage(pageInfo.getPages());
        pageBean.setPageSize(pageInfo.getPageSize());
        pageBean.setPrePage(pageInfo.getPrePage());
        pageBean.setNextPage(pageInfo.getNextPage());
        pageBean.setCurPage(pageInfo.getPageNum());
        pageBean.setData(favoriteList);
        pageBean.setCount(pageInfo.getTotal());
        return new ResultInfo(true,pageBean,null);
    }
}
