package com.riis.currencytraveler.model;

import com.riis.currencytraveler.CountryCurrencyType;

public class CountryCurrency {
    private CountryCurrencyType countryCurrencyType;
    private String currencyRate;

    public CountryCurrency(CountryCurrencyType countryCurrencyType) {
        this.countryCurrencyType = countryCurrencyType;
    }

    public CountryCurrencyType getCountryCurrencyType() {
        return countryCurrencyType;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }
}
