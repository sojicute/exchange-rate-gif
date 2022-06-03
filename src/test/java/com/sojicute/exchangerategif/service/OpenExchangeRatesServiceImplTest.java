package com.sojicute.exchangerategif.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sojicute.exchangerategif.client.OpenExchangeRatesClient;
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
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenExchangeRatesServiceImplTest {

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("openexchangerates.url", wireMockServer::baseUrl);
    }

    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/latest.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("latest.json")));

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/historical/2022-06-01.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("historical.json")));
    }



    @DisplayName("Тест должен вернуть корректную дату 2022-03-02")
    @Test
    void shouldReturnCorrectPreviousDate() {
        // timestamp - 2022-03-02
        Long timestamp = (long) 1654283768 * 1000;
        String date = openExchangeRatesService.getPreviousDate(timestamp);
        assertEquals(date, "2022-03-02");
    }
}