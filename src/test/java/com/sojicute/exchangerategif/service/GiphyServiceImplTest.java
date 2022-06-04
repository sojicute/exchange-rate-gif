package com.sojicute.exchangerategif.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sojicute.exchangerategif.client.GiphyClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GiphyServiceImplTest {

    @Autowired
    private GiphyClient giphyClient;

    @Autowired
    private GiphyService giphyService;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("giphy.url", wireMockServer::baseUrl);
    }

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/random"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("gif.json")));
    }


    @DisplayName("Тест должен корректно вернуть гиф")
    @Test
    void shouldReturnRandomGif() {
        JsonNode gif = giphyService.getRandomGif("");
        assertEquals("gif", gif.path("data").path("type").asText());
        assertEquals("https://giphy.com/embed/U240fth9ypuKR2RGyp", gif.path("data").path("embed_url").asText());
    }
}