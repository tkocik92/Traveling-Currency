package com.riis.currencytraveler.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riis.currencytraveler.BuildConfig;
import com.riis.currencytraveler.CountryCurrencyType;
import com.riis.currencytraveler.model.CountryCurrency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = 23 // Robolectric doesn't support API 24 yet :(
)
public class CurrencySpinnerAdapterTest {
    private CurrencySpinnerAdapter currencySpinnerAdapter;

    @Before
    public void setUp() {
        Context context = Mockito.mock(Context.class);
        LayoutInflater layoutInflater = Mockito.mock(LayoutInflater.class);
        View view = Mockito.mock(TextView.class);

        Mockito.when(layoutInflater.inflate(android.R.layout.simple_spinner_item, Mockito.mock(ViewGroup.class), false))
                .thenReturn(view);
        Mockito.when(layoutInflater.inflate(android.R.layout.simple_spinner_dropdown_item, Mockito.mock(ViewGroup.class), false))
                .thenReturn(view);

        Mockito.when(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(layoutInflater);

        List<CountryCurrency> countryCurrencies = new ArrayList<>();
        countryCurrencies.add(new CountryCurrency(CountryCurrencyType.GBP));

        currencySpinnerAdapter = new CurrencySpinnerAdapter(context, countryCurrencies);
    }

    @Test
    public void OverrideTest() {
        assertEquals(1, currencySpinnerAdapter.getCount());
        assertNotNull(currencySpinnerAdapter.getItem(0));
        assertEquals(0, currencySpinnerAdapter.getItemId(1));
    }
}
