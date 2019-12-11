package com.sifo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.sifo.entity.Goods;
import com.sifo.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    ISearchService searchService;

    /**
     * 根据关键字到索引库中查商品
     * @param keyword
     * @param map
     * @return
     */
    @RequestMapping("/searchByKeyword")
    public String searchByKeyword(Page<Goods> iPage, String keyword, ModelMap map)
    {
       // System.out.println("关键字keyword:"+keyword);
        IPage<Goods> searchPage = searchService.querySolr(iPage,keyword);
        map.put("searchPage",searchPage);

        Map<String,Object> map1=new HashMap<>();
        map1.put("keyword",keyword);
        map.put("params",new Gson().toJson(map1));

        return "search_list";
    }


}
