package com.crm.vo;

import java.util.List;

/**
 * @Classname PaginationVO
 * @Date 2020/3/22 17:16
 * @Created by Falling Stars
 * @Description 分页查询专门封装一个VO对象
 */
public class PaginationVO<T> {
    /**
     * 总记录数
     */
    private int total;

    /**
     * 当前页的信息列表
     */
    private List<T> dataList;

    /**
     * 获取总记录数
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 获取当前页的信息列表
     *
     * @return
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * 设置当前页的信息列表
     *
     * @param dataList
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
