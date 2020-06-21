package com.ego.dubbo.service.impl;

import com.ego.dao.TbUserMapper;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

import javax.annotation.Resource;
import java.util.List;

public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Resource
    private TbUserMapper tbUserMapper;
    @Override
    public TbUser selByUser(TbUser user) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
