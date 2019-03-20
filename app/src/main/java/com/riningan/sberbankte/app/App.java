package com.riningan.sberbankte.app;

import android.app.Application;

import com.riningan.sberbankte.data.CurrenciesRepositoryImpl;
import com.riningan.sberbankte.domain.CurrenciesInteractor;
import com.riningan.sberbankte.domain.CurrenciesInteractorImpl;


public class App extends Application {
    private CurrenciesInteractor mInteractor = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public CurrenciesInteractor getInteractor() {
        if (mInteractor == null) {
            mInteractor = new CurrenciesInteractorImpl(new CurrenciesRepositoryImpl());
        }
        return mInteractor;
    }
}
