package com.sojicute.exchangerategif.service;

import com.sojicute.exchangerategif.client.OpenExchangeRatesClient;
import com.sojicute.exchangerategif.model.ConversionRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {

    @Value("${openexchangerates.base}")
    private String base;

    @Value("${openexchangerates.api-key}")
    private String apiId;

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Override
    public String getTag(String code) {
        if (code == null) {
            return "error";
        }

        Double cr = getCurrentRateByCode(code);
        Double pr = getPreviousRateByCode(code);

        if (cr == null || pr == null) {
            return "error";
        }

        switch (Double.compare(cr, pr)) {
            case 1:
                return "rich";
            case 0:
                return "balance";
            case -1:
                return "broke";
            default:
                return "error";
        }
    }

    @Override
    public ConversionRates getCurrentRates() {
        return openExchangeRatesClient.getConversionRates(apiId, base);
    }

    @Override
    public ConversionRates getPreviousRates() {
        String date = getPreviousDate(System.currentTimeMillis());
        return openExchangeRatesClient.getHistoricalConversionRates(apiId, base, date);
    }

    @Override
    public String getPreviousDate(Long timestamp) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
        LocalDate localDate = LocalDate.parse(currentDate);
        return localDate.minusDays(1).toString();
    }

    private Double getCurrentRateByCode(String code) {
        return getCurrentRates().getRates().get(code);
    }

    private Double getPreviousRateByCode(String code) {
        return getPreviousRates().getRates().get(code);
    }
}
