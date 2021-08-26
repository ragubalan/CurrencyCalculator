package com.dla.cc.converter.dao;

import com.dla.cc.converter.model.ExchangeRate;
import com.dla.cc.dao.DAO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConverterDAO implements DAO<ExchangeRate> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterDAO.class);

    public final List<ExchangeRate> exchangeRates;

    /**
     * Constructs the list of exchange rates from the "ExchangeRates.txt" file.
     */
    public ConverterDAO() {
        List<ExchangeRate> exchangeRatesList;
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource("ExchangeRates.txt").toURI());
            Stream<String> exchangeRatesAsListFromFile = Files.lines(path);

            AtomicInteger lineCount = new AtomicInteger(1);
            exchangeRatesList = exchangeRatesAsListFromFile.filter(Predicate.not(String::isEmpty)).map(exchangeRate -> {
                lineCount.getAndIncrement();
                String[] values = exchangeRate.split(",");
                if (values.length != 4) {
                    LOGGER.error("ExchangeRates.txt file has invalid data at line " + lineCount);
                }
                if (StringUtils.isBlank(values[0]) || StringUtils.isBlank(values[1]) || StringUtils.isBlank(values[2]) || StringUtils.isBlank(values[3])) {
                    LOGGER.error("ExchangeRates.txt file has empty data at line " + lineCount);
                }
                try {
                    Double.valueOf(values[3].trim());
                } catch (NumberFormatException nfe) {
                    LOGGER.error("ExchangeRates.txt file has invalid rate at line " + lineCount);
                }
                return new ExchangeRate(values[0].trim(), values[1].trim(), values[2].trim(), Double.valueOf(values[3].trim()));
            }).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            exchangeRatesList = null;
            LOGGER.error("Exception while reading ExchangeRates.txt file. The actual error is : {}", e.getMessage());
        }
        exchangeRates = exchangeRatesList;
    }

    @Override
    public final List<ExchangeRate> getAll() {
        return exchangeRates;
    }

    @Override
    public final Optional<ExchangeRate> get(final String code) {
        return exchangeRates.stream().filter(exchangeRate -> StringUtils.equals(code, exchangeRate.getCode())).findFirst();
    }
}