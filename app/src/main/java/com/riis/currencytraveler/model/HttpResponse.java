package com.riis.currencytraveler.model;

public class HttpResponse {
    private int responseCode;
    private String responseBody;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
