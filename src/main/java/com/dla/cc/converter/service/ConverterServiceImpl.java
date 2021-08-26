package com.dla.cc.converter.service;

import com.dla.cc.converter.dao.ConverterDAO;
import com.dla.cc.converter.model.ExchangeRate;
import com.dla.cc.dao.DAO;
import com.dla.cc.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Service implementation of {@link ConverterService}.
 */
@Service
public class ConverterServiceImpl implements ConverterService {
    private static DAO<ExchangeRate> converterDAO = new ConverterDAO();

    @Override
    public final JSONObject getConvertedCurrencyValue(final Double sourceAmount, final String sourceCode, final String targetCode) {
        validateInput(sourceAmount, sourceCode, targetCode);
        Optional<ExchangeRate> sourceCodeExchangeRate = converterDAO.get(sourceCode);
        Optional<ExchangeRate> targetCodeExchangeRate = converterDAO.get(targetCode);
        ExchangeRate sourceExchangeRate = sourceCodeExchangeRate.get();
        ExchangeRate targetExchangeRate = targetCodeExchangeRate.get();
        Double sourceAmountToGBP = sourceAmount / sourceExchangeRate.getRate();
        BigDecimal targetAmountFromGBP = new BigDecimal(sourceAmountToGBP * targetExchangeRate.getRate());
        JSONObject response = new JSONObject();
        response.put("amount", targetAmountFromGBP.setScale(2, RoundingMode.UP));
        response.put("country", targetExchangeRate.getCountry());
        response.put("name", targetExchangeRate.getName());
        return response;
    }

    private final void validateInput(final Double sourceAmount, final String sourceCode, final String targetCode) {
        if (StringUtils.isBlank(sourceCode)) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Source code value cannot be blank or empty.");
        }

        if (StringUtils.isBlank(targetCode)) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Target code value cannot be blank or empty.");
        }

        if (StringUtils.equals(sourceCode, targetCode)) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Source code value cannot be equal to target code value.");
        }

        if (sourceAmount < 0) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Source amount cannot be negative.");
        }

        if (!sourceAmount.toString().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Source amount " + sourceAmount + " is not in currency format.");
        }

        Optional<ExchangeRate> sourceCodeExchangeRate = converterDAO.get(sourceCode);
        if (sourceCodeExchangeRate.isEmpty()) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Source code value " + sourceCode + " is invalid.");
        }

        Optional<ExchangeRate> targetCodeExchangeRate = converterDAO.get(targetCode);
        if (targetCodeExchangeRate.isEmpty()) {
            throw new BadRequestException("ERR_BAD_REQUEST", "Target code value " + targetCode + " is invalid.");
        }
    }
}
