package com.riis.currencytraveler.util;

import com.riis.currencytraveler.CountryCurrencyType;

public interface CurrencyChangedListener {
    void onCurrencyChanged(CountryCurrencyType countryCurrencyType);
}
