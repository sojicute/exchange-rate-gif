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

import java.util.Locale;

@RestController
@RequestMapping("/api/")
public class MainController {

    private final OpenExchangeRatesService openExchangeRatesService;

    private final GiphyService giphyService;

    @Autowired
    public MainController(OpenExchangeRatesService openExchangeRatesService, GiphyService giphyService) {
        this.openExchangeRatesService = openExchangeRatesService;
        this.giphyService = giphyService;
    }

    @GetMapping("/rate/{code}")
    ResponseEntity<JsonNode> getRate(@PathVariable("code") String code) {
        code = code.toUpperCase(Locale.ROOT);
        String tag = openExchangeRatesService.getTag(code);
        JsonNode gif = giphyService.getRandomGif(tag);
        return new ResponseEntity<>(gif, HttpStatus.OK);
    }
}
