package com.ego.dubbo.service.impl;

import com.ego.dao.TbItemCatMapper;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zdd
 * @date 2019-05-18 0:52
 */
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {
    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItemCat> show(long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        return tbItemCatMapper.selectByExample(example);
    }
}
