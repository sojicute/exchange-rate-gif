package com.sojicute.exchangerategif.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface GiphyService {
    JsonNode getRandomGif(String tag);
}
