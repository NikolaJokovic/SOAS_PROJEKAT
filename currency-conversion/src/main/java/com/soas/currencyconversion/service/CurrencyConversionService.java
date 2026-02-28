package com.soas.currencyconversion.service;

import com.soas.api.dto.BankAccountDTO;
import com.soas.api.dto.ConversionResultDTO;
import com.soas.api.dto.ExchangeRateDTO;
import com.soas.api.proxy.BankAccountProxy;
import com.soas.api.proxy.CurrencyExchangeProxy;
import com.soas.util.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {

   private final BankAccountProxy bankAccountProxy;
   private final CurrencyExchangeProxy currencyExchangeProxy;

    public ConversionResultDTO convert(String email, String from, String to, Double quantity) {

        ExchangeRateDTO exchangeRate = currencyExchangeProxy.getExchangeRate(from, to);

        BankAccountDTO account = bankAccountProxy.getAccountByEmail(email);

        Double currentBalance = getBalanceForCurrency(account, from);
        if (currentBalance < quantity) {
            throw new InsufficientFundsException(from, quantity, currentBalance);
        }

        Double convertedAmount = exchangeRate.convert(quantity);

        setBalanceForCurrency(account, from, currentBalance - quantity);
        Double newToBalance = getBalanceForCurrency(account, to) + convertedAmount;
        setBalanceForCurrency(account, to, newToBalance);

        BankAccountDTO updatedAccount = bankAccountProxy.updateAccount(account.getId(), account);

        String message = String.format(
                "Uspešno izvršena razmena %s: %.2f za %s: %.2f",
                from, quantity, to, convertedAmount
        );

        return ConversionResultDTO.builder()
                .email(email)
                .updatedBalance(updatedAccount)
                .message(message)
                .build();
    }

    private Double getBalanceForCurrency(BankAccountDTO account, String currency) {
        return switch (currency.toUpperCase()) {
            case "EUR" -> account.getEur();
            case "USD" -> account.getUsd();
            case "GBP" -> account.getGbp();
            case "CHF" -> account.getChf();
            case "RSD" -> account.getRsd();
            default -> throw new IllegalArgumentException("Nepoznata valuta: " + currency);
        };
    }

    private void setBalanceForCurrency(BankAccountDTO account, String currency, Double amount) {
        switch (currency.toUpperCase()) {
            case "EUR" -> account.setEur(amount);
            case "USD" -> account.setUsd(amount);
            case "GBP" -> account.setGbp(amount);
            case "CHF" -> account.setChf(amount);
            case "RSD" -> account.setRsd(amount);
            default -> throw new IllegalArgumentException("Nepoznata valuta: " + currency);
        }
    }
}