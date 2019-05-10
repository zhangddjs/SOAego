package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zdd
 * @date 2019-05-09 21:27
 */
@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;        //自动往接口注入实现类。

    /**
     * 分页显示商品
     */
    @RequestMapping("item/list")
    @ResponseBody
    public EasyUIDataGrid show(int page, int rows) {
        return tbItemServiceImpl.show(page, rows);
    }

    /**
     * 商品修改
     * @return
     */
    @RequestMapping("rest/page/item-edit")
    public String showItemEdit() {
        return "item-edit";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 3);
        if (index == 1)
            er.setStatus(200);
        return er;
    }
    /**
     * 商品上架
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 1);
        if (index == 1)
            er.setStatus(200);
        return er;
    }
    /**
     * 商品下架
     * @param ids
     * @return
     */
    @RequestMapping("rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 2);
        if (index == 1)
            er.setStatus(200);
        return er;
    }
}
