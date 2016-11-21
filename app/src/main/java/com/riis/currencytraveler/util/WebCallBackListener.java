package com.riis.currencytraveler.util;

import com.riis.currencytraveler.model.HttpResponse;

public interface WebCallBackListener {
    void onCallStart();
    void onSuccess(HttpResponse httpResponse);
    void onFail(HttpResponse httpResponse);
}
