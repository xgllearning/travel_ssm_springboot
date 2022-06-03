package com.heima.travel.service.impl;

import com.heima.travel.mapper.CategoryMapper;
import com.heima.travel.pojo.Category;
import com.heima.travel.service.CategoryService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author laofang
 * @description
 * @date 2021-06-19
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultInfo findAllCategory() {
       List<Category> categoryList=  this.categoryMapper.findAllCategory();
       return new ResultInfo(true,categoryList,null);
    }
}
