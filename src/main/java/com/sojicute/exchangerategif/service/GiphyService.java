package com.sojicute.exchangerategif.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface GiphyService {
    JsonNode getRandomGif(String tag);
}
