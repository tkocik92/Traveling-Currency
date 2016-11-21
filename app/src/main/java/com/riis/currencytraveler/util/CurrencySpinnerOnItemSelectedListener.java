package com.riis.currencytraveler.util;

import android.view.View;
import android.widget.AdapterView;

import com.riis.currencytraveler.model.CountryCurrency;

public class CurrencySpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private CurrencyChangedListener currencyChangedListener;

    public CurrencySpinnerOnItemSelectedListener(CurrencyChangedListener currencyChangedListener) {
        this.currencyChangedListener = currencyChangedListener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CountryCurrency countryCurrency = (CountryCurrency) parent.getSelectedItem();

        currencyChangedListener.onCurrencyChanged(countryCurrency.getCountryCurrencyType());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
