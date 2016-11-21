package com.riis.currencytraveler.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyConverterTest {
    // These values were real-world values form 11/21/16
    private static final String BRL_RATE = "3.355";
    private static final String EUR_RATE = "0.94065";
    private static final String GBP_RATE = "0.80673";
    private static final String JPY_RATE = "110.61";

    @Test
    public void ConvertUSDToCurrencyTest() {
        assertEquals("0", CurrencyConverter.convertUSDToCurrency("", BRL_RATE));
        assertEquals("17", CurrencyConverter.convertUSDToCurrency("5", BRL_RATE));
        assertEquals("5", CurrencyConverter.convertUSDToCurrency("5", EUR_RATE));
        assertEquals("5", CurrencyConverter.convertUSDToCurrency("5", GBP_RATE));
        assertEquals("554", CurrencyConverter.convertUSDToCurrency("5", JPY_RATE));
    }

    @Test
    public void ConvertCurrencyToUSDTest() {
        assertEquals("0", CurrencyConverter.convertCurrencyToUSD("", BRL_RATE));
        assertEquals("2", CurrencyConverter.convertCurrencyToUSD("5", BRL_RATE));
        assertEquals("6", CurrencyConverter.convertCurrencyToUSD("5", EUR_RATE));
        assertEquals("7", CurrencyConverter.convertCurrencyToUSD("5", GBP_RATE));
        assertEquals("1", CurrencyConverter.convertCurrencyToUSD("5", JPY_RATE));
    }
}
