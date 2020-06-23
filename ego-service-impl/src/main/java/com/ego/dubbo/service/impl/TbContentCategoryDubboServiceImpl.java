package com.ego.dubbo.service.impl;

import com.ego.dao.TbContentCategoryMapper;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

import javax.annotation.Resource;
import java.util.List;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selByPid(long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    public int insTbContentCategory(TbContentCategory cate) {
        return tbContentCategoryMapper.insertSelective(cate);
    }

    @Override
    public int updIsParentById(TbContentCategory cate) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(cate);
    }

    @Override
    public TbContentCategory selById(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }
}
