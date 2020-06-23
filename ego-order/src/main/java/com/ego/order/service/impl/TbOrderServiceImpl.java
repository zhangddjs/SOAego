package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TbOrderServiceImpl implements TbOrderService {
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.cart.key}")
    private String cartKey;
    @Value("${passport.url}")
    private String passportUrl;
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;
    @Override
    public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String result = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
        String key = cartKey + ((LinkedHashMap)er.getData()).get("username");
        String json = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);

        List<TbItemChild> listNew = new ArrayList<>();
        for (TbItemChild child : list) {
            for (long id : ids){
                if(child.getId() == id){
                    //判断购买量是否大于等于库存
                    TbItem tbItem = tbItemDubboServiceImpl.selById(id);
                    child.setEnough(tbItem.getNum() >= child.getNum());
                    listNew.add(child);
                }
            }
        }
        return listNew;
    }

    @Override
    public EgoResult create(MyOrderParam param, HttpServletRequest request) {
        //订单数据
        TbOrder order = new TbOrder();
        order.setPayment(param.getPayment());
        order.setPaymentType(param.getPaymentType());
        long id = IDUtils.genItemId();
        order.setOrderId(id + "");
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);

        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String result = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
        Map user = (LinkedHashMap)er.getData();
        order.setUserId(Long.parseLong(user.get("id").toString()));
        order.setBuyerNick(user.get("username").toString());
        order.setBuyerRate(0);
        //订单-商品表
        for (TbOrderItem item : param.getOrderItems()) {
            item.setId(IDUtils.genItemId() + "");
            item.setOrderId(id + "");
        }
        //收货人信息
        TbOrderShipping shipping = param.getOrderShipping();
        shipping.setOrderId(id + "");
        shipping.setCreated(date);
        shipping.setUpdated(date);

        EgoResult egoResult = new EgoResult();
        try {
            int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), shipping);
            if(index > 0){
                egoResult.setStatus(200);
                //删除购买的商品
                String json = jedisDaoImpl.get(cartKey + user.get("username"));
                List<TbItemChild> listCart = JsonUtils.jsonToList(json, TbItemChild.class);
                List<TbItemChild> listCartNew = new ArrayList<>();
                for (TbItemChild child : listCart) {
                    for (TbOrderItem item : param.getOrderItems()) {
                        if(child.getId() == Long.parseLong(item.getItemId())){
                            listCartNew.add(child);
                        }
                    }
                }

                for (TbItemChild mynew : listCartNew) {
                    listCart.remove(mynew);
                }
                //删除
                jedisDaoImpl.set(cartKey + user.get("username"), JsonUtils.objectToJson(listCart));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return egoResult;
    }
}
