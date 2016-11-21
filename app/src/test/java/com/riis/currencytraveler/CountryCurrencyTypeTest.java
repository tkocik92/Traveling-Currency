package com.riis.currencytraveler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryCurrencyTypeTest {
    @Test
    public void GetCountryCurrencyResourceIdTest() {
        assertEquals(R.string.brl, CountryCurrencyType.BRL.getCountryCurrencyResourceId());
        assertEquals(R.string.eur, CountryCurrencyType.EUR.getCountryCurrencyResourceId());
        assertEquals(R.string.gbp, CountryCurrencyType.GBP.getCountryCurrencyResourceId());
        assertEquals(R.string.jpy, CountryCurrencyType.JPY.getCountryCurrencyResourceId());
        assertEquals(R.string.usd, CountryCurrencyType.USD.getCountryCurrencyResourceId());
    }
}
