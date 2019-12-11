package com.sifo.shop_goods;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//提供者声明交换机
@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("goods_exchange");
    }
}
