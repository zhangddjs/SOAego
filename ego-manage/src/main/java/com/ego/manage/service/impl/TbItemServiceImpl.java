package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zdd
 * @date 2019-05-09 21:19
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Value("${search.url}")
    private String url;

    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.item.key}")
    private String itemKey;


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
            if(status == 2 || status == 3){
                jedisDaoImpl.del(itemKey+id);
            }
        }
        if(index == idsStr.length) {
            return 1;
        }

        return 0;
    }

    @Override
    public int save(TbItem item, String desc, String itemParams) throws Exception {
        //不考虑事务回滚
/*        long id = IDUtils.genItemId();      //随机生成id
        item.setId(id);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte) 1);
        int index = tbItemDubboServiceImpl.insTbItem(item);
        if(index > 0) {     //添加描述,如果下面失败，上面成功，则不具备回滚功能
            TbItemDesc itemDesc = new TbItemDesc();
            itemDesc.setItemDesc(desc);
            itemDesc.setItemId(id);
            itemDesc.setCreated(date);
            itemDesc.setUpdated(date);
            index += tbItemDescDubboServiceImpl.insDesc(itemDesc);
        }
        if (index == 2){
            return 1;
        }
*/


        //调用dubbo中考虑事务回滚功能方法
        long id = IDUtils.genItemId();      //随机生成id
        item.setId(id);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte) 1);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(id);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);

        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setCreated(date);
        paramItem.setUpdated(date);
        paramItem.setItemId(id);
        paramItem.setParamData(itemParams);

        int index = 0;
        index = tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc, paramItem);
        System.out.println("index:" + index);

        final TbItem itemFinal = item;
        final String descFinal = desc;
        new Thread(){
            public void run(){
                Map<String, Object> map = new HashMap<>();
                map.put("item", itemFinal);
                map.put("desc", descFinal);
                HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
            }
        }.start();

        return index;
    }

}
