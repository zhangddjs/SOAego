package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zdd
 * @date 2019-05-08 22:36
 */
public class EasyUIDataGrid implements Serializable {
    //当前页显示数据
    private List<?> rows;
    //总条数
    private long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public EasyUIDataGrid() {}

    public EasyUIDataGrid(List<?> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    @Override
    public String toString() {
        return "EasyUIDataGrid{" +
                "rows=" + rows +
                ", total=" + total +
                '}';
    }
}
