package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;

/**
 * @author zdd
 * @date 2019-05-07 18:31
 */
public interface TbItemService {
    /**
     * 显示商品
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page, int rows);
}
