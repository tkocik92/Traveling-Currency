package com.riis.currencytraveler.service;

import com.riis.currencytraveler.BuildConfig;
import com.riis.currencytraveler.model.HttpResponse;
import com.riis.currencytraveler.util.WebCallBackListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = 23 // Robolectric doesn't support API 24 yet :(
)
public class FetchConversionsTaskTest {
    private FetchConversionsTask fetchConversionsTask;
    private WebCallBackListener webCallBackListener;

    @Before
    public void setUp() {
        webCallBackListener = Mockito.mock(WebCallBackListener.class);
        HttpHelper httpHelper = Mockito.mock(HttpHelper.class);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseCode(200);

        Mockito.when(httpHelper.getCurrencyConversion(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(httpResponse);

        fetchConversionsTask = new FetchConversionsTask(webCallBackListener);
        fetchConversionsTask.setHttpHelper(httpHelper);
    }

    @Test
    public void ExecuteTest() {
        HttpResponse httpResponse = null;
        try {
            httpResponse = fetchConversionsTask.execute().get();
        } catch (InterruptedException | ExecutionException ignored) {}

        assertEquals(404, httpResponse.getResponseCode());

        Mockito.verify(webCallBackListener).onCallStart();
        Mockito.verify(webCallBackListener).onFail(Mockito.any(HttpResponse.class));

        try {
            httpResponse = fetchConversionsTask.execute("GBP", ".5").get();
        } catch (InterruptedException | ExecutionException ignored) {}

        assertEquals(200, httpResponse.getResponseCode());
        Mockito.verify(webCallBackListener, Mockito.times(2)).onCallStart();
        Mockito.verify(webCallBackListener).onSuccess(Mockito.any(HttpResponse.class));
    }
}
