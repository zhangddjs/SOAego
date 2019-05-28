package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dao.TbItemParamMapper;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zdd
 * @date 2019-05-27 15:48
 */
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Resource
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public EasyUIDataGrid showPage(int page, int rows) {
        //先设置分页条件
        PageHelper.startPage(page, rows);
        //设置查询的sql语句
        //xxxExample() 设置了什么就相当于在sql中where从句中添加了条件。

        //如果表中有一个或一个以上的列是text类型，生成的方法WithBlobs()
        //如果使用xxxWithBlobs() 查询结果中包含带有text类的列，如果没有使用withBlobs() 方法不带有text类型。
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        //根据程序员自己编写的sql语句结合分页插件并产生最终结果，封装到PageInfo
        PageInfo<TbItemParam> pi = new PageInfo<>(list);

        //设置方法返回结果
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public int delByIds(String ids) throws Exception {
        int index = 0;
        String[] idStr = ids.split(",");
        for (String id : idStr) {
            index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        if(index == idStr.length)
            return 1;
        else throw new Exception("删除失败，可能原因：数据已经不存在");       //回滚，比如说，其他管理员已经删除一条，而你这里还存在。
    }

    @Override
    public TbItemParam selByCatid(long catId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0){
            return list.get(0);        //要求每一个类目只有一个模板
        }
        return null;
    }

    @Override
    public int insParam(TbItemParam param) {
        return tbItemParamMapper.insertSelective(param);
    }

}
