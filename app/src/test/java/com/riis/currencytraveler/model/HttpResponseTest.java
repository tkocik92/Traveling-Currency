package com.riis.currencytraveler.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HttpResponseTest {
    private HttpResponse httpResponse;

    @Before
    public void setUp() {
        httpResponse = new HttpResponse();
    }

    @Test
    public void ClassTest() {
        httpResponse.setResponseCode(200);
        assertEquals(200, httpResponse.getResponseCode());

        httpResponse.setResponseBody("{}");
        assertNotNull(httpResponse.getResponseBody());
    }
}
