package com.sojicute.exchangerategif.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sojicute.exchangerategif.service.GiphyService;
import com.sojicute.exchangerategif.service.OpenExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class MainController {

    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;

    @Autowired
    private GiphyService giphyService;


    @GetMapping("/result/{code}")
    ResponseEntity<JsonNode> getResultGif(@PathVariable("code") String code) {
        String tag = openExchangeRatesService.getTag(code);
        JsonNode gif = giphyService.getRandomGif(tag);
        return new ResponseEntity<>(gif, HttpStatus.OK);
    }
}
