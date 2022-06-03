package com.sojicute.exchangerategif.client;

import com.sojicute.exchangerategif.model.ConversionRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Клиент который совершает API запросы в внешнему серсвису openexchangerates.org
 */
@FeignClient(value = "openexchangerates", url = "${openexchangerates.url}")
public interface OpenExchangeRatesClient {

    /**
     * Метод запрашивает актуальный курс валюты
     *
     * @param appId api токен
     * @param base код валюты
     */
    @GetMapping("/latest.json")
    ConversionRates getConversionRates(@RequestParam("app_id") String appId,
                                       @RequestParam("base") String base);

    /**
     * Метод запрашивает курс валюты за указаную дату yyyy-MM-dd
     *
     * @param appId api токен
     * @param base код валюты
     * @param date дата в формате yyyy-MM-dd
     */
    @GetMapping("/historical/{date}.json")
    ConversionRates getHistoricalConversionRates(@RequestParam("app_id") String appId,
                                                 @RequestParam("base") String base,
                                                 @PathVariable("date") String date);
}
