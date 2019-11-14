package com.cpren.utils.pageHelper;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/10/23
 */
public class PageHelperUtils {

    /** 组建分页结果 */
    public static <T> Pagination<T> pagResult(int pageNum, int pageSize, int totalHits, List<T> smartSearchVOList) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(smartSearchVOList);
        // 总记录数
        pageInfo.setTotal(totalHits);
        // 页码
        pageInfo.setPageNum(pageNum);
        // 页大小
        pageInfo.setPageSize(pageSize);
        // 总页数
        pageInfo.setPages(totalHits / pageSize + (totalHits % pageSize != 0 ? 1 : 0));
        return new Pagination<>(pageInfo);
    }
}
