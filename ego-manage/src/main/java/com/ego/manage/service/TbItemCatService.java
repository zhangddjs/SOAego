package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;

import java.util.List;

/**
 * @author zdd
 * @date 2019-05-18 1:04
 */
public interface TbItemCatService {
    /**
     * 根据父菜单id显示所有子菜单
     * @param pid
     * @return
     */
    List<EasyUITree> show(long pid);
}
