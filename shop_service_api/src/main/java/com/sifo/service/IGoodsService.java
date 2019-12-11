package com.sifo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sifo.entity.Goods;

import java.util.List;


public interface IGoodsService {

    IPage<Goods> getGoodsList();

    void insert(Goods goods);

    List<Goods> getList();

    Goods queryById(Integer id);
}
