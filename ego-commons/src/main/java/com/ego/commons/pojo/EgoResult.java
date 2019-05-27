package com.ego.commons.pojo;

/**
 * @author zdd
 * @date 2019-05-11 0:13
 */
public class EgoResult {
    private int status;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
