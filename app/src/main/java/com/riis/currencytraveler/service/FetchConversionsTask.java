package com.riis.currencytraveler.service;

import android.os.AsyncTask;

import com.riis.currencytraveler.model.HttpResponse;
import com.riis.currencytraveler.util.WebCallBackListener;

public class FetchConversionsTask extends AsyncTask<String, Void, HttpResponse> {
    private HttpHelper httpHelper;
    private WebCallBackListener webCallBackListener;

    public FetchConversionsTask(WebCallBackListener webCallBackListener) {
        this.webCallBackListener = webCallBackListener;
    }

    void setHttpHelper(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        webCallBackListener.onCallStart();
    }

    @Override
    protected HttpResponse doInBackground(String... params) {
        if (params.length != 2) {
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setResponseCode(404);
            httpResponse.setResponseBody("Parameters not specified");
            return httpResponse;
        }

        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }

        return httpHelper.getCurrencyConversion(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        super.onPostExecute(httpResponse);

        if (httpResponse.getResponseCode() == 200) {
            webCallBackListener.onSuccess(httpResponse);
        } else {
            webCallBackListener.onFail(httpResponse);
        }
    }
}
