package com.sojicute.exchangerategif.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sojicute.exchangerategif.client.GiphyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiphyServiceImpl implements GiphyService {

    @Value("${giphy.api-key}")
    private String api_key;

    @Autowired
    private GiphyClient giphyClient;

    @Override
    public JsonNode getRandomGif(String tag) {
        return giphyClient.getGiphyGif(api_key, tag);
    }
}
