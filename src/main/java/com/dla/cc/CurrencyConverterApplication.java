package com.dla.cc;

import com.dla.cc.converter.service.ConverterService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dla.cc"})
public class CurrencyConverterApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterApplication.class);

    @Autowired
    private ConverterService conversionService;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter currency amount : ");
        double amount = scanner.nextDouble();
        System.out.print("Enter source currency code: ");
        String sourceCurrencyCode = scanner.next();
        System.out.print("Enter target currency code: ");
        String targetCurrencyCode = scanner.next();
        JSONObject response = conversionService.getConvertedCurrencyValue(amount, sourceCurrencyCode, targetCurrencyCode);
        String newline = System.lineSeparator();
        System.out.print(newline + "Source Amount : " + amount + newline);
        System.out.print("Converted Target Amount : " + response.get("amount") + newline);
    }


}
