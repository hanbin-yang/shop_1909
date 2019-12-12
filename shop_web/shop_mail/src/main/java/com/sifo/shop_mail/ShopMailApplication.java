package com.sifo.shop_mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sifo")
public class ShopMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMailApplication.class, args);
    }

}
