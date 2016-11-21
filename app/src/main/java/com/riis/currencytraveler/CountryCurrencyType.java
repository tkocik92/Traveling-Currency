package com.riis.currencytraveler;

import android.support.annotation.StringRes;

public enum CountryCurrencyType {
    BRL(R.string.brl),
    EUR(R.string.eur),
    GBP(R.string.gbp),
    JPY(R.string.jpy),
    USD(R.string.usd);

    private int countryCurrencyResourceId;

    CountryCurrencyType(@StringRes int countryCurrencyResourceId) {
        this.countryCurrencyResourceId = countryCurrencyResourceId;
    }

    public int getCountryCurrencyResourceId() {
        return countryCurrencyResourceId;
    }
}
