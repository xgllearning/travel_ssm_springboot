package com.heima.travel.controller;

import com.heima.travel.service.CategoryService;
import com.heima.travel.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/findAllCategory")
    public ResultInfo findAllCategory(){
        return this.categoryService.findAllCategory();
    }
}
