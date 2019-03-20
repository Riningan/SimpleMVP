package com.riningan.sberbankte.data;

import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public interface CurrenciesRepository {
    List<Currency> getCurrencies() throws Exception;

    float convert(float value, Currency input, Currency output) throws Exception;
}
