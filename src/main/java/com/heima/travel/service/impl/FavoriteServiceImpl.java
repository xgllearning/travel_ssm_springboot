package com.heima.travel.service.impl;

import com.heima.travel.mapper.FavoriteMapper;
import com.heima.travel.mapper.RouteMapper;
import com.heima.travel.pojo.User;
import com.heima.travel.service.FavoriteService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

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


}
