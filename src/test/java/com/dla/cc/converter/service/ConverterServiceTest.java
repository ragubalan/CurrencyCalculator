package com.dla.cc.converter.service;

import com.dla.cc.exception.BadRequestException;
import com.dla.cc.utils.JSONObjectUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ConverterService}.
 */
@ExtendWith(MockitoExtension.class)
public class ConverterServiceTest {
    @InjectMocks
    private ConverterServiceImpl converterService;

    /**
     * Should validate the input.
     */
    @ParameterizedTest
    @CsvSource({"1.11,,AED,ERR_BAD_REQUEST,Source code value cannot be blank or empty.",
            "1.11, ,AED,ERR_BAD_REQUEST,Source code value cannot be blank or empty.",
            "1.11,AED,,ERR_BAD_REQUEST,Target code value cannot be blank or empty.",
            "1.11,AED,   ,ERR_BAD_REQUEST,Target code value cannot be blank or empty.",
            "1.11,AED,AED,ERR_BAD_REQUEST,Source code value cannot be equal to target code value.",
            "1.11,DUB,AED,ERR_BAD_REQUEST,Source code value DUB is invalid.",
            "1.11,AED,DUB,ERR_BAD_REQUEST,Target code value DUB is invalid.",
            "-1.11,AED,BGN,ERR_BAD_REQUEST,Source amount cannot be negative.",
            "0.1234,AED,BGN,ERR_BAD_REQUEST,Source amount 0.1234 is not in currency format."})
    public final void shouldValidateInput(final String sourceAmount, final String sourceCode,
                                          final String targetCode, final String errorCode, final String errorMessage) {
        BadRequestException exception = assertThrows(BadRequestException.class, () -> converterService.getConvertedCurrencyValue(Double.valueOf(sourceAmount), sourceCode, targetCode));
        String errorMessageAsJSON = JSONObjectUtils.createErrorJson(errorCode, errorMessage);
        assertEquals(errorMessageAsJSON, exception.getMessage());
    }

    /**
     * Should calculate the target amount.
     */
    @ParameterizedTest
    @CsvSource({"1000,AED,BGN,361.91,Bulgaria,Leva",
            "361.91,BGN,AED,1000.02,United Arab Emirates, Dirhams"})
    public final void shouldReturnResponse(final String sourceAmount, final String sourceCode,
                                           final String targetCode, final String responseAmount,
                                           final String responseCountry, final String responseName) {
        JSONObject response = converterService.getConvertedCurrencyValue(Double.valueOf(sourceAmount), sourceCode, targetCode);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(Double.valueOf(responseAmount)), response.get("amount"));
        assertEquals(responseCountry, response.get("country"));
        assertEquals(responseName, response.get("name"));
    }
}
