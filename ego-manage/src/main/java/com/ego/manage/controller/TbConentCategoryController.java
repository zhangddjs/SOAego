package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TbConentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;

    /**
     * 查询商品类目
     * @param id
     * @return
     */
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUITree> showCategory(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.showCategory(id);
    }

    /**
     * 新增内容类目
     * @param cate
     * @return
     */
    @RequestMapping("content/category/create")
    @ResponseBody
    public EgoResult create(TbContentCategory cate){
        return tbContentCategoryServiceImpl.create(cate);
    }

    /**
     * 重命名
     * @param cate
     * @return
     */
    @RequestMapping("content/category/update")
    @ResponseBody
    public EgoResult update(TbContentCategory cate){
        return tbContentCategoryServiceImpl.update(cate);
    }

    /**
     * 删除
     * @param cate
     * @return
     */
    @RequestMapping("content/category/delete")
    @ResponseBody
    public EgoResult delete(TbContentCategory cate){
        return tbContentCategoryServiceImpl.delete(cate);
    }
}
