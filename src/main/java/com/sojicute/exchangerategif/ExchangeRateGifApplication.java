package com.sojicute.exchangerategif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeRateGifApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateGifApplication.class, args);
    }

}
