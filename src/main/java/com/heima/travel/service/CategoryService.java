package com.heima.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.travel.pojo.Category;
import com.heima.travel.vo.ResultInfo;


public interface CategoryService extends IService<Category> {
    ResultInfo findAllCategory();
}
