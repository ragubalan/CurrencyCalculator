package com.dla.cc.converter.model;

/**
 * POJO to hold the exchange rate.
 */
public class ExchangeRate {
    private final String country;
    private final String name;
    private final String code;
    private final Double rate;

    public ExchangeRate(final String newCountry, final String newName, final String newCode, final Double newRate) {
        this.country = newCountry;
        this.name = newName;
        this.code = newCode;
        this.rate = newRate;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Double getRate() {
        return rate;
    }
}
