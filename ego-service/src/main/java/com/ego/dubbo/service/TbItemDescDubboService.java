package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @author zdd
 * @date 2019-05-27 10:05
 */
public interface TbItemDescDubboService {
    /**
     * 新增
     * @param itemDesc
     * @return
     */
    int insDesc(TbItemDesc itemDesc);

    /**
     * 根据主键查商品描述对象
     * @param itemid
     * @return
     */
    TbItemDesc selByItemid(long itemid);
}
