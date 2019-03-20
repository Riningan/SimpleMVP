package com.riningan.sberbankte.presentation;


import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public interface MainView {
    void setCurrencies(List<Currency> currencies);

    void showLoadCurrenciesError(String message);

    void showConvertError(String message);

    void showConvertMessage(float value);
}
