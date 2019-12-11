package com.sifo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sifo.entity.Goods;
import com.sifo.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Reference
    IGoodsService goodsService;

    @RequestMapping("showById")
    public String showById(Integer id, Model model)
    {
        Goods goods=goodsService.queryById(id);
        model.addAttribute("goods",goods);
        return "goods_item";
    }

}
