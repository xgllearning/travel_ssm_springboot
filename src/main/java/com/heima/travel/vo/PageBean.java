package com.heima.travel.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 */
public class PageBean<T> implements Serializable {
    private List<T> data;//当前页列表
    private Integer firstPage=1;//首页
    private Integer prePage;//上一页
    private Integer curPage;//当前页
    private Integer nextPage;//下一页
    private Integer totalPage;//总页数
    private Long count;//总记录数
    private Integer pageSize;//每页多少条

    public PageBean() {
    }

    public PageBean(List<T> data, Integer firstPage, Integer prePage, Integer curPage, Integer nextPage, Integer totalPage, Long count, Integer pageSize) {
        this.data = data;
        this.firstPage = firstPage;
        this.prePage = prePage;
        this.curPage = curPage;
        this.nextPage = nextPage;
        this.totalPage = totalPage;
        this.count = count;
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
