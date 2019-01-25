package com.yzz.lr.model.request;

import java.io.Serializable;

/**
 * 查询基础参数
 * @author zhizhuang.yang
 * @date 2017年9月8日
 * @version 1.0.0
 * @description 查询基础参数
 */
public class RequestParam implements Serializable{

    private final static long serialVersionUID = 1L;

    private Integer pageIndex = 1;

    private Integer pageSize = Integer.MAX_VALUE;

    /**
     * 按哪个字段排序
     */
    private String sorting;

    /**
     * 排序规则，默认顺序
     */
    private String sortType = "asc";

    /**
     * 是否已删除,默认未删除
     */
    private boolean isDelete = false;


    /**
     * 返回行数
     * @return
     */
    public Integer getLimitOffset(){
        return this.pageSize;
    }

    /**
     * 返回第几条开始
     * @return
     */
    public Integer getLimitRows(){
        return (this.pageIndex-1)*this.pageSize;
    }

    public String getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        this.sorting = sorting;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean delete) {
        isDelete = delete;
    }
}
