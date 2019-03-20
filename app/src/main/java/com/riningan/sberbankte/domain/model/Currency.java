package com.riningan.sberbankte.domain.model;

import android.support.annotation.NonNull;


public class Currency {
    public String id;
    public String name;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
