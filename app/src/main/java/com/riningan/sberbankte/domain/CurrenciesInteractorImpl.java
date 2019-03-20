package com.riningan.sberbankte.domain;


import android.util.Pair;

import com.riningan.sberbankte.data.CurrenciesRepository;
import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public class CurrenciesInteractorImpl implements CurrenciesInteractor {
    private CurrenciesRepository mRepository;


    public CurrenciesInteractorImpl(CurrenciesRepository repository) {
        mRepository = repository;
    }


    @Override
    public void loadCurrencies(Callback<List<Currency>> callback) {
        LoadCurrenciesTask task = new LoadCurrenciesTask(callback);
        task.execute(mRepository);
    }

    @Override
    public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
        ConvertTask task = new ConvertTask(callback);
        task.execute(mRepository, value, input, output);
    }


    static class LoadCurrenciesTask extends BaseTask<CurrenciesRepository, List<Currency>> {
        LoadCurrenciesTask(Callback<List<Currency>> callback) {
            super(callback);
        }

        @Override
        protected Pair<List<Currency>, Throwable> doInBackground(CurrenciesRepository... params) {
            CurrenciesRepository repository = params[0];
            try {
                return new Pair<>(repository.getCurrencies(), null);
            } catch (Throwable throwable) {
                return new Pair<>(null, throwable);
            }
        }
    }

    static class ConvertTask extends BaseTask<Object, Float> {
        ConvertTask(Callback<Float> callback) {
            super(callback);
        }

        @Override
        protected Pair<Float, Throwable> doInBackground(Object... params) {
            CurrenciesRepository repository = (CurrenciesRepository) params[0];
            float value = (float) params[1];
            Currency input = (Currency) params[2];
            Currency output = (Currency) params[3];
            try {
                return new Pair<>(repository.convert(value, input, output), null);
            } catch (Throwable throwable) {
                return new Pair<>(null, throwable);
            }
        }
    }
}
