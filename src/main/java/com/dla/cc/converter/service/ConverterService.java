package com.dla.cc.converter.service;

import org.json.simple.JSONObject;

/**
 * Service to perform currency conversion.
 */
public interface ConverterService {
    /**
     * Returns the value of the source amount converted to the target
     * currency, rounded to 2 decimal places (rounded up), together with the target country and name.
     *
     * @param sourceAmount : A source currency amount in the format x.yy
     * @param sourceCode   : A source ISO 4217 currency code
     * @param targetCode   :  A target ISO 4217 currency code
     * @return the value of the source amount converted to the target
     * currency, rounded to 2 decimal places (rounded up), together with the target country and name.
     */
    JSONObject getConvertedCurrencyValue(Double sourceAmount, String sourceCode, String targetCode);
}
