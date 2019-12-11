package com.sifo.listener;


import com.alibaba.dubbo.config.annotation.Reference;
import com.sifo.entity.Goods;
import com.sifo.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Reference
    ISearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "goods_exchange", type = "fanout"),
            value = @Queue(name = "search_queue")
    ))
    public void msgHandler(Goods goods)
    {
        System.out.println(goods);
        searchService.insertSolr(goods);
    }
}
