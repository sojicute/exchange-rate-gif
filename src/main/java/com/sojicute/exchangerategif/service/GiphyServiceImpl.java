package com.sojicute.exchangerategif.service;

import com.sojicute.exchangerategif.client.GiphyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GiphyServiceImpl implements GiphyService {

    @Value("${giphy.api-key}")
    private String api_key;

    @Autowired
    private GiphyClient giphyClient;

    @Override
    public Map<String, String> getRandomGif(String tag) {
        return giphyClient.getGiphyGif(api_key, tag);
    }
}
