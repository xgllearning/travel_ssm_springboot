package com.heima.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.travel.mapper.CategoryMapper;
import com.heima.travel.pojo.Category;
import com.heima.travel.service.CategoryService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResultInfo findAllCategory() {
        //1.先从redis查询缓存的数据 约定key为CATES,values：List<Category>
        String key="CATES";
        List<Category> categoryList=null;
        String  data = (String) redisTemplate.opsForValue().get(key);
        if (data==null) {
            categoryList = this.list();
            //存入redis，设置过期时间
            String values = new Gson().toJson(categoryList);
            //将数据存入到redis中，kes为约定key,设置过期时间为30分钟
            redisTemplate.opsForValue().set(key,values,30, TimeUnit.MINUTES);

        }else {
            //如果不等于null,则把redis中的数据序列化返回，然后在赋值给categoryList，redis中数据就是json格式
            categoryList=  new Gson().fromJson(data,new TypeToken<List<Category>>(){}.getType());
            //刷新过期时间
            redisTemplate.expire(key,30,TimeUnit.MINUTES);
        }
//       List<Category> categoryList=  this.categoryMapper.findAllCategory();

//       List<Category> categoryList=  this.categoryMapper.findAllCategory();
        //List<Category> categoryList = this.list();
        return new ResultInfo(true,categoryList,null);
    }
}
