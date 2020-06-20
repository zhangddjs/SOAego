package com.ego.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TbItemController {

    @RequestMapping("item/{id}.html")
    public String showItemDetails(@PathVariable long id){

        return "item";
    }

}
