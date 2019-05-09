package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dao.TbItemMapper;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zdd
 * @date 2019-05-09 0:43
 */
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {

        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());

        //分页代码
        //设置分页条件
        PageHelper.startPage(page, rows);

        PageInfo<TbItem> pi = new PageInfo<>(list);
        //此时分页插件生效，pi中包含所有分页相关信息。

        //放入到实体类
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }
}
