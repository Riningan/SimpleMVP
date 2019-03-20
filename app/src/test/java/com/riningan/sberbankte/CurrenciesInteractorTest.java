package com.riningan.sberbankte;

import com.riningan.sberbankte.data.CurrenciesRepository;
import com.riningan.sberbankte.domain.CurrenciesInteractor;
import com.riningan.sberbankte.domain.CurrenciesInteractorImpl;
import com.riningan.sberbankte.domain.model.Currency;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(RobolectricTestRunner.class)
public class CurrenciesInteractorTest {
    private static Currency mCurrency1 = new Currency();
    private static Currency mCurrency2 = new Currency();
    private static List<Currency> mCurrencies = new ArrayList<>();


    @BeforeClass
    public static void setUpClass() {
        mCurrency1.id = "1";
        mCurrency1.name = "currency1";
        mCurrency2.id = "2";
        mCurrency2.name = "currency2";
        mCurrencies.add(mCurrency1);
        mCurrencies.add(mCurrency2);
    }


    @Test
    public void loadCurrencies_success() {
        CurrenciesInteractorImpl currenciesInteractor = new CurrenciesInteractorImpl(new CurrenciesRepository() {
            @Override
            public List<Currency> getCurrencies() {
                return mCurrencies;
            }

            @Override
            public float convert(float value, Currency input, Currency output) {
                return 0;
            }
        });

        currenciesInteractor.loadCurrencies(new CurrenciesInteractor.Callback<List<Currency>>() {
            @Override
            public void onSuccess(List<Currency> value) {
                assertEquals(2, value.size());
                assertEquals(value.get(0), mCurrency1);
                assertEquals(value.get(1), mCurrency2);
            }

            @Override
            public void onError(Throwable throwable) {
                assertEquals(1,0);
            }
        });
    }

    @Test
    public void loadCurrencies_error() {
        CurrenciesInteractorImpl currenciesInteractor = new CurrenciesInteractorImpl(new CurrenciesRepository() {
            @Override
            public List<Currency> getCurrencies() throws Exception {
                throw new Exception();
            }

            @Override
            public float convert(float value, Currency input, Currency output) {
                return 0;
            }
        });

        currenciesInteractor.loadCurrencies(new CurrenciesInteractor.Callback<List<Currency>>() {
            @Override
            public void onSuccess(List<Currency> value) {
                assertEquals(1,0);
            }

            @Override
            public void onError(Throwable throwable) {
                assertNotNull(throwable);
            }
        });
    }

    @Test
    public void convert_success() {
        CurrenciesInteractorImpl currenciesInteractor = new CurrenciesInteractorImpl(new CurrenciesRepository() {
            @Override
            public List<Currency> getCurrencies() {
                return null;
            }

            @Override
            public float convert(float value, Currency input, Currency output) {
                return value;
            }
        });

        currenciesInteractor.convert(12, mCurrency1, mCurrency2, new CurrenciesInteractor.Callback<Float>() {
            @Override
            public void onSuccess(Float value) {
                assertEquals(12,12);
            }

            @Override
            public void onError(Throwable throwable) {
                assertEquals(1,0);
            }
        });
    }

    @Test
    public void convert_error() {
        CurrenciesInteractorImpl currenciesInteractor = new CurrenciesInteractorImpl(new CurrenciesRepository() {
            @Override
            public List<Currency> getCurrencies() {
                return null;
            }

            @Override
            public float convert(float value, Currency input, Currency output) throws Exception {
                throw new Exception();
            }
        });

        currenciesInteractor.convert(12, mCurrency1, mCurrency2, new CurrenciesInteractor.Callback<Float>() {
            @Override
            public void onSuccess(Float value) {
                assertEquals(1,0);
            }

            @Override
            public void onError(Throwable throwable) {
                assertNotNull(throwable);
            }
        });
    }
}