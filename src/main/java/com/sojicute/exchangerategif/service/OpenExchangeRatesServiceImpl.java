package com.sojicute.exchangerategif.service;

import com.sojicute.exchangerategif.client.OpenExchangeRatesClient;
import com.sojicute.exchangerategif.model.ConversionRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Сервис для обработки данных из внешеного источника
 *
 *
 * Поля up, down, zero, error определяются исходя конфигурационного файла,
 * в том случае если мы захотим поменять тег не изменяя текущий код.
 */
@Service
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {

    @Value("${openexchangerates.base}")
    private String base;

    @Value("${openexchangerates.api-key}")
    private String apiId;

    @Value("${giphy.tag.up}")
    private String up;

    @Value("${giphy.tag.down}")
    private String down;

    @Value("${giphy.tag.zero}")
    private String zero;

    @Value("${giphy.tag.error}")
    private String error;

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    /**
     * Метод сравниваниет актуальный курс валюты с курсом валюты предыдущего дня
     * и возвращает результат в виде тега.
     *
     * @param code код валюты
     * @return тег
     */
    @Override
    public String getTag(String code) {
        if (code == null) {
            return error;
        }

        Double cr = getCurrentRateByCode(code);
        Double pr = getPreviousRateByCode(code);

        if (cr == null || pr == null) {
            return error;
        }

        switch (Double.compare(cr, pr)) {
            case 1:
                return up;
            case 0:
                return zero;
            case -1:
                return down;
            default:
                return error;
        }
    }


    /**
     * Метод возвращает актуальный курс валюты
     */
    @Override
    public ConversionRates getCurrentRates() {
        return openExchangeRatesClient.getConversionRates(apiId, base);
    }

    /**
     * Метод возвращает курс валюты за предыдущий день относительно текущей даты.
     *
     * Дата определятся исходя из текущего времени ОС в миллисекундах.
     */
    @Override
    public ConversionRates getPreviousRates() {
        String date = getPreviousDate(System.currentTimeMillis());
        return openExchangeRatesClient.getHistoricalConversionRates(apiId, base, date);
    }

    /**
     * Метод вычисляет дату предыдущего дня.
     *
     * @param timestamp текущая дата
     * @return дата предыдущего дня в формате yyyy-MM-dd
     */
    @Override
    public String getPreviousDate(Long timestamp) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
        LocalDate localDate = LocalDate.parse(currentDate);
        return localDate.minusDays(1).toString();
    }

    /**
     * Проостой метод для выбор конкретной валюты за текущий день
     * @param code код валюты
     * @return курс валюты
     */
    private Double getCurrentRateByCode(String code) {
        return getCurrentRates().getRates().get(code);
    }


    /**
     * Проостой метод для выбор конкретной валюты за предыдущий день
     * @param code код валюты
     * @return курс валюты
     */
    private Double getPreviousRateByCode(String code) {
        return getPreviousRates().getRates().get(code);
    }
}
