package com.riis.currencytraveler.util;

import java.math.BigDecimal;

public class CurrencyConverter {
    /**
     * @param usdAmount
     *      A string value of the USD Amount
     * @param currencyRate
     *      A string value of the currency rate
     * @return
     *      A string value of amount in the desired currency
     */
    public static String convertUSDToCurrency(String usdAmount, String currencyRate) {
        if (usdAmount.isEmpty()) {
            return "0";
        }

        BigDecimal usDollars = new BigDecimal(usdAmount);
        BigDecimal conversionRate = new BigDecimal(currencyRate);

        BigDecimal result = usDollars.multiply(conversionRate);
        BigDecimal displayableResult = result.setScale(0, BigDecimal.ROUND_UP);

        return displayableResult.toPlainString();
    }

    /**
     * @param currencyAmount
     *      A string value of the currency amount
     * @param currencyRate
     *      A string value of the currency rate from USD -> Currency
     * @return
     *      A string value of converted amount in USD.
     */
    public static String convertCurrencyToUSD(String currencyAmount, String currencyRate) {
        if (currencyAmount.isEmpty()) {
            return "0";
        }

        BigDecimal amount = new BigDecimal(currencyAmount);
        BigDecimal conversionRate = new BigDecimal(currencyRate);

        BigDecimal result = amount.divide(conversionRate, BigDecimal.ROUND_UP);

        return result.toPlainString();
    }
}
