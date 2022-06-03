package com.sojicute.exchangerategif.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sojicute.exchangerategif.client.OpenExchangeRatesClient;
import com.sojicute.exchangerategif.model.ConversionRates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OpenExchangeRatesServiceImplTest {

    private final String base = "";

    private final String apiId = "";

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

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/latest.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("latest.json")));

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/historical/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("historical.json")));
    }

    @DisplayName("Тест должен извлечь акутальный обменный курс")
    @Test
    void shouldRetrieveCurrentRates() {
        ConversionRates cr = openExchangeRatesClient.getConversionRates(apiId, base);
        assertEquals(1654156800, (long) cr.getTimestamp(), "Должен вернуть корректный timestamp");
        assertEquals("USD", cr.getBase(), "Должен вернуть корректный код валюты");
        assertEquals(0.935804, cr.getRates().get("EUR"), "Должен вернуть корректный курс валюты");
    }

    @DisplayName("Тест должен извлечь обменный курс за предыдущий день")
    @Test
    void shouldRetrievePreviousRates() {
        ConversionRates cr = openExchangeRatesClient.getHistoricalConversionRates(apiId, base, "2022-06-01");
        assertEquals(1654127998, (long) cr.getTimestamp(), "Должен вернуть корректный timestamp");
        assertEquals("USD", cr.getBase(), "Должен вернуть корректный код валюты");
        assertEquals(0.938853, cr.getRates().get("EUR"), "Должен вернуть корректный курс валюты");
    }

    @DisplayName("Тест должен вернуть тег 'rich'")
    @Test
    void shouldReturnTagRich_getTag() {
        String tag = openExchangeRatesService.getTag("ALL");
        assertEquals(tag, "rich");
    }

    @DisplayName("Тест должен вернуть тег 'broke'")
    @Test
    void shouldReturnTagBroke_getTag() {
        String tag = openExchangeRatesService.getTag("AMD");
        assertEquals(tag, "broke");
    }

    @DisplayName("Тест должен вернуть тег 'balance'")
    @Test
    void shouldReturnTagBalance_getTag() {
        String tag = openExchangeRatesService.getTag("AED");
        assertEquals(tag, "balance");
    }

    @DisplayName("Тест должен вернуть тег 'error'")
    @Test
    void shouldReturnTagError_getTag() {
        String tag = openExchangeRatesService.getTag("INVALID CODE");
        assertEquals(tag, "error");
    }

    @DisplayName("Тест должен вернуть корректную дату предыдущего дня")
    @Test
    void shouldReturnCorrectPreviousDate() {
        // timestamp - 2022-03-02
        Long timestamp = (long) 1654283768 * 1000;
        String date = openExchangeRatesService.getPreviousDate(timestamp);
        assertEquals("2022-06-02", date);
    }
}