package com.ego.dubbo.service.impl;

import com.ego.dao.TbItemParamItemMapper;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

import javax.annotation.Resource;
import java.util.List;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public TbItemParamItem selByItemid(long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }
}
