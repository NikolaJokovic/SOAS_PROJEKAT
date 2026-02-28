package com.soas.util.exception;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String from, String to) {
        super("Kurs razmene za " + from + " → " + to + " nije pronađen");
    }
}