package com.cpren.utils.pageHelper;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;

/**
 * @author cdxu@iyunwen.com on 2018/8/14
 */
public class Pagination<T> extends PageSerializable<T> {

    private static final long serialVersionUID = 6103720062680505647L;

    private int pageNum;
    private int pageSize;
    private int pages;

    public Pagination(PageInfo<T> pageInfo) {
        this.total = pageInfo.getTotal();
        this.list = pageInfo.getList();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.pages = pageInfo.getPages();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
