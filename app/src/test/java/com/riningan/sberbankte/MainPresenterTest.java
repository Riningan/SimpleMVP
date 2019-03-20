package com.riningan.sberbankte;

import android.util.Pair;

import com.riningan.sberbankte.domain.CurrenciesInteractor;
import com.riningan.sberbankte.domain.model.Currency;
import com.riningan.sberbankte.presentation.MainPresenter;
import com.riningan.sberbankte.presentation.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {
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


    private MainView mMainView = null;
    private List<Pair<String, Object>> mMethods = new ArrayList<>();
    private MainPresenter mMainPresenter = null;


    @Before
    public void setUp() {
        mMainView = new MainView() {
            @Override
            public void setCurrencies(List<Currency> currencies) {
                mMethods.add(new Pair<String, Object>("setCurrencies", currencies));
            }

            @Override
            public void showLoadCurrenciesError(String message) {
                mMethods.add(new Pair<String, Object>("showLoadCurrenciesError", message));
            }

            @Override
            public void showConvertError(String message) {
                mMethods.add(new Pair<String, Object>("showConvertError", message));
            }

            @Override
            public void showConvertMessage(float value) {
                mMethods.add(new Pair<String, Object>("showConvertMessage", value));
            }
        };
        mMethods.clear();
    }

    @After
    public void tearDown() {
        if (mMainPresenter != null) {
            mMainPresenter.detachView();
        }
    }


    @Test
    public void viewResumed_success() {
        CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor() {
            @Override
            public void loadCurrencies(Callback<List<Currency>> callback) {
                callback.onSuccess(mCurrencies);
            }

            @Override
            public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
            }
        };
        mMainPresenter = new MainPresenter(currenciesInteractor);
        mMainPresenter.attachView(mMainView);

        mMainPresenter.viewResumed();

        assertEquals(1, mMethods.size());
        assertEquals("setCurrencies", mMethods.get(0).first);
        assertEquals(mCurrencies, mMethods.get(0).second);
    }

    @Test
    public void viewResumed_error() {
        CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor() {
            @Override
            public void loadCurrencies(Callback<List<Currency>> callback) {
                callback.onError(new Exception("Error"));
            }

            @Override
            public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
            }
        };
        mMainPresenter = new MainPresenter(currenciesInteractor);
        mMainPresenter.attachView(mMainView);

        mMainPresenter.viewResumed();

        assertEquals(1, mMethods.size());
        assertEquals("showLoadCurrenciesError", mMethods.get(0).first);
        assertEquals("Error", mMethods.get(0).second);
    }

    @Test
    public void convert_success() {
        CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor() {
            @Override
            public void loadCurrencies(Callback<List<Currency>> callback) {
            }

            @Override
            public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
                callback.onSuccess(value);
            }
        };
        mMainPresenter = new MainPresenter(currenciesInteractor);
        mMainPresenter.attachView(mMainView);

        mMainPresenter.convert("12", mCurrency1, mCurrency2);

        assertEquals(1, mMethods.size());
        assertEquals("showConvertMessage", mMethods.get(0).first);
        assertEquals(12f, mMethods.get(0).second);
    }

    @Test
    public void convert_error_1() {
        CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor() {
            @Override
            public void loadCurrencies(Callback<List<Currency>> callback) {
            }

            @Override
            public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
            }
        };
        mMainPresenter = new MainPresenter(currenciesInteractor);
        mMainPresenter.attachView(mMainView);

        mMainPresenter.convert("", mCurrency1, mCurrency2);

        assertEquals(1, mMethods.size());
        assertEquals("showConvertError", mMethods.get(0).first);
        assertEquals("empty String", mMethods.get(0).second);
    }

    @Test
    public void convert_error_2() {
        CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor() {
            @Override
            public void loadCurrencies(Callback<List<Currency>> callback) {
            }

            @Override
            public void convert(float value, Currency input, Currency output, Callback<Float> callback) {
                callback.onError(new Exception("Error"));
            }
        };
        mMainPresenter = new MainPresenter(currenciesInteractor);
        mMainPresenter.attachView(mMainView);

        mMainPresenter.convert("12", mCurrency1, mCurrency2);

        assertEquals(1, mMethods.size());
        assertEquals("showConvertError", mMethods.get(0).first);
        assertEquals("Error", mMethods.get(0).second);
    }
}
