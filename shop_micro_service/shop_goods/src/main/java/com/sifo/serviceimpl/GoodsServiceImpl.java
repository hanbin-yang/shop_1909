package com.sifo.serviceimpl;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sifo.dao.GoodsImagesMapper;
import com.sifo.dao.GoodsMapper;
import com.sifo.entity.Goods;
import com.sifo.entity.GoodsImages;
import com.sifo.service.IGoodsService;

import com.sifo.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsImagesMapper goodsImagesMapper;

    @Reference
    ISearchService searchService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    @Transactional(readOnly = true)
    public IPage<Goods> getGoodsList() {

        Page<Goods> page=new Page<>(1,10);


        IPage<Goods> goodsIPage = goodsMapper.selectPage(page, null);

        return goodsIPage;
    }

    //事务管理
    @Transactional
    @Override
    public void insert(Goods goods) {

        goodsMapper.insert(goods);

        GoodsImages goodsImages=new GoodsImages();
        goodsImages.setGid(goods.getId())
                    .setIsfengmian(1)
                    .setUrl(goods.getFmUrl())
                    .setStatus(1);
        goodsImagesMapper.insert(goodsImages);

        for (String url : goods.getOtherImgUrl()) {
            GoodsImages gi=new GoodsImages();
            gi.setGid(goods.getId())
                    .setIsfengmian(0)
                    .setUrl(url)
                    .setStatus(1);
            goodsImagesMapper.insert(gi);
        }
        //同步索引库
        //searchService.insertSolr(goods);
        //通过交换机发送商品对象信息，在消费端进行同步索引库消费操作
        rabbitTemplate.convertAndSend("goods_exchange","",goods);
    }

    @Override
    public List<Goods> getList() {
        return goodsMapper.queryList();
    }

    @Override
    public Goods queryById(Integer id) {
        return goodsMapper.queryById(id);
    }
}
