package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;

/**
 * @author zdd
 * @date 2019-05-07 22:03
 */
public interface TbItemDubboService {
    /**
     * 商品分页查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page, int rows);
}
