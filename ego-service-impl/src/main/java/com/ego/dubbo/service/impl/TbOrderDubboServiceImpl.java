package com.ego.dubbo.service.impl;

import com.ego.dao.TbOrderItemMapper;
import com.ego.dao.TbOrderMapper;
import com.ego.dao.TbOrderShippingMapper;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import javax.annotation.Resource;
import java.util.List;

public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Override
    public int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception {
        int index = tbOrderMapper.insertSelective(order);
        for (TbOrderItem tbOrderItem : list) {
            index += tbOrderItemMapper.insertSelective(tbOrderItem);
        }
        index += tbOrderShippingMapper.insertSelective(shipping);

        if(index == 2 + list.size()){
            return 1;
        } else {
            throw new Exception("创建订单失败");
        }

    }
}
