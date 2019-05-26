package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zdd
 * @date 2019-05-24 21:21
 */
@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    /**
     * 显示商品类目
     * @param id
     * @return
     * 第一请求没有参数，希望查询出所有一级菜单，一级菜单的父id为0
     */
    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUITree> showCat(@RequestParam(defaultValue = "0") long id){
        return tbItemCatServiceImpl.show(id);
    }
}
