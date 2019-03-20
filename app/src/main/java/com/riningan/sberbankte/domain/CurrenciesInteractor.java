package com.riningan.sberbankte.domain;

import android.os.AsyncTask;
import android.util.Pair;

import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


public interface CurrenciesInteractor {
    void loadCurrencies(Callback<List<Currency>> callback);

    void convert(float value, Currency input, Currency output, Callback<Float> callback);


    interface Callback<T> {
        void onSuccess(T value);

        void onError(Throwable throwable);
    }


    abstract class BaseTask<P, T> extends AsyncTask<P, Void, Pair<T, Throwable>> {
        private final Callback<T> mCallback;


        BaseTask(Callback<T> callback) {
            mCallback = callback;
        }


        @Override
        protected void onPostExecute(Pair<T, Throwable> result) {
            if (result.first == null) {
                mCallback.onError(result.second);
            } else {
                mCallback.onSuccess(result.first);
            }
        }
    }
}
