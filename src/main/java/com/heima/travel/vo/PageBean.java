package com.heima.travel.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageBean<T> implements Serializable {
    private List<T> data;//当前页列表
    private Integer firstPage=1;//首页
    private Integer prePage;//上一页
    private Integer curPage;//当前页
    private Integer nextPage;//下一页
    private Long totalPage;//总页数
    private Long count;//总记录数
    private Long pageSize;//每页多少条


}
