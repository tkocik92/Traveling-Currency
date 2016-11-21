package com.riis.currencytraveler.service;

import android.util.Log;

import com.riis.currencytraveler.model.HttpResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpHelper {

    HttpResponse getCurrencyConversion(String baseCurrency, String requestedCurrencies) {
        HttpResponse httpResponse = new HttpResponse();

        HttpURLConnection httpURLConnection = null;

        try {
            URL targetUrl = new URL("http://api.fixer.io/latest?base=" + baseCurrency + "&symbols=" + requestedCurrencies);

            httpURLConnection = (HttpURLConnection) targetUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpResponse.setResponseCode(httpURLConnection.getResponseCode());

            InputStream inputStream;
            if (httpResponse.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            } else {
                inputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String lineRead;
            while ((lineRead = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineRead);
            }

            httpResponse.setResponseBody(stringBuilder.toString());
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Could not set up web call", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return httpResponse;
    }
}
