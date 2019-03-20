package com.riningan.sberbankte.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.riningan.sberbankte.domain.model.Currency;

import java.util.List;


class CurrenciesAdapter extends ArrayAdapter<Currency> {
    CurrenciesAdapter(@NonNull Context context, @NonNull List<Currency> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }
}
