package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dao.TbItemDescMapper;
import com.ego.dao.TbItemMapper;
import com.ego.dao.TbItemParamItemMapper;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
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

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        //分页代码
        //设置分页条件
        System.out.println("cccccc");

        PageHelper.startPage(page, rows);

        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());


        PageInfo<TbItem> pi = new PageInfo<>(list);
        //此时分页插件生效，pi中包含所有分页相关信息。

        //放入到实体类
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());
        return dataGrid;
    }

    @Override
    public int updItemStatus(TbItem tbItem) {
        //为null的不操作
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int insTbItem(TbItem tbItem) {
        return tbItemMapper.insert(tbItem);
    }

    @Override
    public int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem paramItem) throws Exception {
        int index = 0;
        //正常情况这里通过spring aop事务包围生效，但是这里try catch，用Index接收，异常就回滚。
        try{
            index = tbItemMapper.insertSelective(tbItem);
            index += tbItemDescMapper.insertSelective(desc);
            index += tbItemParamItemMapper.insertSelective(paramItem);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(index == 3)
            return 1;
        else {
            throw new Exception("新增失败,数据还原");
        }
    }

    @Override
    public List<TbItem> selAllByStatus(byte status) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(example);
    }

    @Override
    public TbItem selById(long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
