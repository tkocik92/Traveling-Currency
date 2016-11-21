package com.riis.currencytraveler.util;

import android.view.View;
import android.widget.AdapterView;

import com.riis.currencytraveler.CountryCurrencyType;
import com.riis.currencytraveler.model.CountryCurrency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CurrencySpinnerOnItemSelectedListenerTest {
    private CurrencyChangedListener currencyChangedListener;
    private CurrencySpinnerOnItemSelectedListener currencySpinnerOnItemSelectedListener;

    @Before
    public void setUp() {
        currencyChangedListener = Mockito.mock(CurrencyChangedListener.class);
        currencySpinnerOnItemSelectedListener = new CurrencySpinnerOnItemSelectedListener(currencyChangedListener);
    }

    @Test
    public void OnItemSelectedTest() {
        AdapterView<?> adapterView = Mockito.mock(AdapterView.class);
        Mockito.when(adapterView.getSelectedItem()).thenReturn(new CountryCurrency(CountryCurrencyType.GBP));

        currencySpinnerOnItemSelectedListener.onItemSelected(adapterView, Mockito.mock(View.class), 0, 0);

        Mockito.verify(currencyChangedListener).onCurrencyChanged(CountryCurrencyType.GBP);
    }

    @Test
    public void EmptyMethodTest() {
        currencySpinnerOnItemSelectedListener.onNothingSelected(Mockito.mock(AdapterView.class));
    }
}
