package com.soas.util.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) { super(message);}

    public InsufficientFundsException(String currency, Double required, Double available) {
        super(String.format(
                "Nedovoljno sredstava. Potrebno: %.2f %s, Dostupno: %.2f %s",
                required, currency, available, currency
        ));
    }
}