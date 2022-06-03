package com.sojicute.exchangerategif.service;

import com.sojicute.exchangerategif.model.ConversionRates;

public interface OpenExchangeRatesService {

    String getTag(String code);

    ConversionRates getCurrentRates();

    ConversionRates getPreviousRates();

    String getPreviousDate(Long timestamp);
}
