package com.riis.currencytraveler.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.riis.currencytraveler.BuildConfig;
import com.riis.currencytraveler.CountryCurrencyType;
import com.riis.currencytraveler.model.CountryCurrency;
import com.riis.currencytraveler.model.HttpResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = 23 // Robolectric doesn't support API 24 yet :(
)
public class MainPresenterTest {
    private MainActivity mainActivity;
    private MainPresenter mainPresenter;

    @SuppressLint("CommitPrefEdits")
    @Before
    public void setUp() {
        mainActivity = Mockito.mock(MainActivity.class);

        SharedPreferences sharedPreferences = Mockito.mock(SharedPreferences.class);
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(sharedPreferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("1");

        Mockito.when(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor);
        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Mockito.when(mainActivity.getSharedPreferences("currency", Context.MODE_PRIVATE))
                .thenReturn(sharedPreferences);

        mainPresenter = new MainPresenter(mainActivity);
    }

    @Test
    public void ConvertUSDToCurrencyTest() {
        mainPresenter.setCountryCurrencyType(CountryCurrencyType.BRL);
        assertEquals("0", mainPresenter.convertUSDToCurrency("0"));
        assertEquals(CountryCurrencyType.BRL, mainPresenter.getCountryCurrencyType());
    }

    @Test
    public void ConvertCurrencyToUSDTest() {
        mainPresenter.setCountryCurrencyType(CountryCurrencyType.BRL);
        assertEquals("0", mainPresenter.convertCurrencyToUSD("0"));
        assertEquals(CountryCurrencyType.BRL, mainPresenter.getCountryCurrencyType());
    }

    @Test
    public void OnCallStartTest() {
        mainPresenter.onCallStart();

        Mockito.verify(mainActivity).startProgressDialog();
    }

    @Test
    public void OnSuccessTest() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseCode(200);
        httpResponse.setResponseBody("{\"base\":\"USD\",\"date\":\"2016-11-21\",\"rates\":" +
                "{\"BRL\":3.355,\"GBP\":0.80637,\"JPY\":110.61,\"EUR\":0.94065}}");

        mainPresenter.onSuccess(httpResponse);

        Mockito.verify(mainActivity).displayConversionRates(Mockito.anyListOf(CountryCurrency.class));
        Mockito.verify(mainActivity).dismissProgressDialog();
    }

    @Test
    public void OnFailTest() {
        mainPresenter.onFail(new HttpResponse());

        Mockito.verify(mainActivity).dismissProgressDialog();
        Mockito.verify(mainActivity).failedToFetchConversionRates();
    }
}
