package com.sifo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sifo.entity.Goods;

public interface ISearchService {

    /**
     * 添加索引库
     * @param goods
     * @return
     */
    int insertSolr(Goods goods);

    /**
     * 根据关键字查询商品集合
     * @param keyword
     * @return
     */
    IPage<Goods> querySolr(Page<Goods> iPage, String keyword);


}
