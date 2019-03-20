package com.riningan.sberbankte.data;

import com.riningan.sberbankte.data.model.ValCurs;
import com.riningan.sberbankte.data.model.Valute;
import com.riningan.sberbankte.domain.model.Currency;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CurrenciesRepositoryImpl implements CurrenciesRepository {
    private ValCurs mCache = null;


    @Override
    public List<Currency> getCurrencies() throws Exception {
        ValCurs valCurs = getResponse();
        if (valCurs.valutes == null) {
            throw new NullPointerException("no valutes");
        } else {
            List<Currency> currencies = new ArrayList<>();
            for (Valute valute : valCurs.valutes) {
                Currency currency = new Currency();
                currency.id = valute.ID;
                currency.name = valute.Name;
                currencies.add(currency);
            }
            return currencies;
        }
    }

    @Override
    public float convert(float value, Currency input, Currency output) throws Exception {
        ValCurs valCurs = getResponse();
        if (valCurs.valutes == null) {
            throw new NullPointerException("no valutes");
        } else {
            Valute inputValute = valCurs.getValuteById(input.id);
            Valute outputValute = valCurs.getValuteById(output.id);
            if (inputValute != null && outputValute != null) {
                float inputValue = inputValute.getValue() / (float) inputValute.Nominal * value;
                if (Float.isInfinite(inputValue)) {
                    throw new NumberFormatException("bad value");
                }
                float outputValue = inputValue / outputValute.getValue() / (float) outputValute.Nominal;
                if (Float.isInfinite(outputValue)) {
                    throw new NumberFormatException("bad value");
                }
                return outputValue;
            }
            throw new NullPointerException("no input or output valute information");
        }
    }


    private ValCurs getResponse() throws Exception {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String serverResponse = readStream(urlConnection.getInputStream());
                serverResponse = serverResponse.substring("<?xml version=\"1.0\" encoding=\"windows-1251\"?>".length()).trim();
                Serializer serializer = new Persister();
                mCache = serializer.read(ValCurs.class, serverResponse);
                return mCache;
            } else {
                throw new IOException("bad server response");
            }
        } catch (Exception e) {
            if (mCache == null) {
                throw e;
            } else {
                return mCache;
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "windows-1251"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
