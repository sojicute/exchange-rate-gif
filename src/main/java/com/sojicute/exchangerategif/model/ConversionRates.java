package com.sojicute.exchangerategif.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Objects;

/**
 * Класс отвечает за хранения response обмена валюты
 */
@Data
public class ConversionRates {

    private String disclaimer;

    private String license;

    private Long timestamp;

    private String base;

    private HashMap<String, Double> rates;
}
