package com.sojicute.exchangerategif.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * Клиент для обращения к внешнему сервису api.giphy.com/v1/gifs
 */
@FeignClient(name = "giphy", url = "${giphy.url}")
public interface GiphyClient {

    /**
     * Метод возвращает случайную гифку по заданому тегу
     *
     * @param apiKey api токен
     * @param tag тег
     */
    @GetMapping("/random")
    Map<String, String> getGiphyGif(@RequestParam("api_key") String apiKey,
                                    @RequestParam("tag") String tag);
}
