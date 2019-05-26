package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @author zdd
 * @date 2019-05-18 0:47
 */
public interface TbItemCatDubboService {
    /**
     * 根据父类目id查询所有子类目
     * @param pid
     * @return
     */
    List<TbItemCat> show(long pid);
}
