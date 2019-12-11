package com.sifo.shop_search_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sifo")
@DubboComponentScan("com.sifo.serviceimpl")
public class ShopSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSearchServiceApplication.class, args);
    }

}
