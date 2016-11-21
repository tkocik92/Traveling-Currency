package com.riis.currencytraveler.model;

import com.riis.currencytraveler.CountryCurrencyType;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CountryCurrencyTest {
    private CountryCurrency countryCurrency;

    @Before
    public void setUp() {
        countryCurrency = new CountryCurrency(CountryCurrencyType.GBP);
    }

    @Test
    public void ClassTest() {
        assertEquals(CountryCurrencyType.GBP, countryCurrency.getCountryCurrencyType());

        countryCurrency.setCurrencyRate("1.423");
        assertNotNull(countryCurrency.getCurrencyRate());
    }
}
