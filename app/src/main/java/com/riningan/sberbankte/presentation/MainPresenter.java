package com.riningan.sberbankte.presentation;

import com.riningan.sberbankte.domain.CurrenciesInteractor;
import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public class MainPresenter {
    private MainView mView = null;
    private CurrenciesInteractor mInteractor;


    public MainPresenter(CurrenciesInteractor interactor) {
        mInteractor = interactor;
    }


    public void attachView(MainView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }


    public void viewResumed() {
        mInteractor.loadCurrencies(new CurrenciesInteractor.Callback<List<Currency>>() {
            @Override
            public void onSuccess(List<Currency> currencies) {
                if (mView != null) {
                    mView.setCurrencies(currencies);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (mView != null) {
                    mView.showLoadCurrenciesError(throwable.getMessage());
                }
            }
        });
    }


    public void convert(String value, Currency input, Currency output) {
        try {
            float valueFloat = Float.parseFloat(value);
            mInteractor.convert(valueFloat, input, output, new CurrenciesInteractor.Callback<Float>() {
                @Override
                public void onSuccess(Float value) {
                    mView.showConvertMessage(value);
                }

                @Override
                public void onError(Throwable throwable) {
                    mView.showConvertError(throwable.getMessage());
                }
            });
        } catch (Exception e) {
            if (mView != null) {
                mView.showConvertError(e.getMessage());
            }
        }
    }
}
