package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;

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

    /**
     * 根据id修改状态
     * @param tbItem
     * @return
     */
    int updItemStatus(TbItem tbItem);

    /**
     * 商品新增
     * @param tbItem
     * @return
     */
    int insTbItem(TbItem tbItem);
}
