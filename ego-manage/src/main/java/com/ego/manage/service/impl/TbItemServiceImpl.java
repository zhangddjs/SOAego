package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Service;


/**
 * @author zdd
 * @date 2019-05-09 21:19
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Override
    public EasyUIDataGrid show(int page, int rows) {
        System.out.println("bbbbbb");
        return tbItemDubboServiceImpl.show(page, rows);     //调用到这一层却无法调用dubbo服务
    }
    @Override
    public int update(String ids, byte status) {
        int index = 0;
        TbItem tbItem = new TbItem();
        String[] idsStr = ids.split(",");
        for (String id : idsStr) {
            tbItem.setId(Long.parseLong(id));
            tbItem.setStatus(status);
            index += tbItemDubboServiceImpl.updItemStatus(tbItem);
        }
        if(index == idsStr.length)
            return 1;
        return 0;
    }

}
