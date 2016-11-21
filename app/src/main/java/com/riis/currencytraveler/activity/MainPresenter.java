package com.riis.currencytraveler.activity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.riis.currencytraveler.CountryCurrencyType;
import com.riis.currencytraveler.model.CountryCurrency;
import com.riis.currencytraveler.model.HttpResponse;
import com.riis.currencytraveler.service.FetchConversionsTask;
import com.riis.currencytraveler.util.CurrencyConverter;
import com.riis.currencytraveler.util.WebCallBackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

class MainPresenter implements WebCallBackListener {
    private CountryCurrencyType countryCurrencyType;
    private MainActivity mainActivity;

    MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    CountryCurrencyType getCountryCurrencyType() {
        return countryCurrencyType;
    }

    void setCountryCurrencyType(CountryCurrencyType countryCurrencyType) {
        this.countryCurrencyType = countryCurrencyType;
    }

    void getCurrencyConversions(@NonNull CountryCurrencyType baseCurrency) {
        String requestedCurrencies = getWebUrlParameterList(baseCurrency);

        new FetchConversionsTask(this).execute(baseCurrency.name(), requestedCurrencies);
    }

    String convertUSDToCurrency(String usdAmount) {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("currency", MODE_PRIVATE);

        return CurrencyConverter.convertUSDToCurrency(usdAmount,
                sharedPreferences.getString(countryCurrencyType.name(), "0"));
    }

    String convertCurrencyToUSD(String currencyAmount) {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("currency", MODE_PRIVATE);

        return CurrencyConverter.convertCurrencyToUSD(currencyAmount,
                sharedPreferences.getString(countryCurrencyType.name(), "0"));
    }

    @Override
    public void onCallStart() {
        mainActivity.startProgressDialog();
    }

    @Override
    public void onSuccess(HttpResponse httpResponse) {
        List<CountryCurrency> countryCurrencies = parseCurrencyRateJson(httpResponse.getResponseBody());

        saveCurrencyRates(countryCurrencies);

        mainActivity.displayConversionRates(countryCurrencies);
        mainActivity.dismissProgressDialog();
    }

    @Override
    public void onFail(HttpResponse httpResponse) {
        mainActivity.dismissProgressDialog();
        mainActivity.failedToFetchConversionRates();
    }

    private String getWebUrlParameterList(CountryCurrencyType baseCurrency) {
        String requestedCurrencies = "";

        for (CountryCurrencyType countryCurrencyType : CountryCurrencyType.values()) {
            if (countryCurrencyType != baseCurrency) {
                requestedCurrencies += countryCurrencyType.name() + ",";
            }
        }

        return requestedCurrencies.substring(0, requestedCurrencies.length() - 1);
    }

    private List<CountryCurrency> parseCurrencyRateJson(String jsonBody) {
        List<CountryCurrency> countryCurrencies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            JSONObject ratesObject = jsonObject.getJSONObject("rates");
            JSONArray tagsArray = ratesObject.names();

            for (int i = 0; i < tagsArray.length(); i++) {
                String tag = tagsArray.getString(i);

                CountryCurrency countryCurrency = new CountryCurrency(CountryCurrencyType.valueOf(tag));
                countryCurrency.setCurrencyRate(ratesObject.getString(tag));
                countryCurrencies.add(countryCurrency);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countryCurrencies;
    }

    private void saveCurrencyRates(List<CountryCurrency> countryCurrencies) {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("currency", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (CountryCurrency countryCurrency : countryCurrencies) {
            editor.putString(countryCurrency.getCountryCurrencyType().name(), countryCurrency.getCurrencyRate());
        }

        editor.apply();
    }
}
