package com.ih2ome.hardware_service.service.vo;

import java.util.List;

/**
 * 分页结果对象
 * @param <T>
 */
public class PageResult<T> {

    private Long total;// 总条数

    private List<T> data;// 数据集合

    public PageResult() {
    }

    public PageResult(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
