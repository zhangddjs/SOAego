package com.ego.dubbo.service.impl;

import com.ego.dao.TbItemDescMapper;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.pojo.TbItemDesc;

import javax.annotation.Resource;

/**
 * @author zdd
 * @date 2019-05-27 10:09
 */
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public int insDesc(TbItemDesc itemDesc) {
        return tbItemDescMapper.insert(itemDesc);
    }
}
