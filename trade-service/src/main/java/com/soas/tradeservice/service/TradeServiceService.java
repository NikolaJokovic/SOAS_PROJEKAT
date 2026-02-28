package com.soas.tradeservice.service;

import com.soas.api.dto.BankAccountDTO;
import com.soas.api.dto.ConversionResultDTO;
import com.soas.api.dto.CryptoWalletDTO;
import com.soas.api.dto.ExchangeRateDTO;
import com.soas.api.proxy.BankAccountProxy;
import com.soas.api.proxy.CryptoWalletProxy;
import com.soas.api.proxy.CurrencyExchangeProxy;
import com.soas.tradeservice.entity.TradeService;
import com.soas.tradeservice.repository.TradeServiceRepository;
import com.soas.util.exception.ExchangeRateNotFoundException;
import com.soas.util.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeServiceService {

    private final TradeServiceRepository tradeCryptoRateRepository;
    private final BankAccountProxy bankAccountProxy;
    private final CryptoWalletProxy cryptoWalletProxy;
    private final CurrencyExchangeProxy currencyExchangeProxy;

    public ConversionResultDTO trade(String email, String from, String to, Double quantity) {
        // Proveri da li je from crypto a to fiat (CRYPTO → FIAT)
        if (isCrypto(from) && isFiat(to)) {
            return convertCryptoToFiat(email, from, to, quantity);
        }
        // Proveri da li je from fiat a to crypto (FIAT → CRYPTO)
        else if (isFiat(from) && isCrypto(to)) {
            return convertFiatToCrypto(email, from, to, quantity);
        }
        else {
            throw new IllegalArgumentException(
                    "Trade servis radi samo razmenu fiat ↔ crypto. " +
                            "From mora biti crypto a to fiat, ili obrnuto."
            );
        }
    }

    // CRYPTO → FIAT (prodaja kripta)
    private ConversionResultDTO convertCryptoToFiat(String email, String crypto, String fiat, Double quantity) {
        // 1. Dobavi crypto novčanik
        CryptoWalletDTO wallet = cryptoWalletProxy.getWalletByEmail(email);

        // 2. Proveri da li ima dovoljno crypto
        Double currentCryptoBalance = getBalanceForCrypto(wallet, crypto);
        if (currentCryptoBalance < quantity) {
            throw new InsufficientFundsException(crypto, quantity, currentCryptoBalance);
        }

        // 3. Prvo razmeni crypto → USD ili EUR
        String intermediateFiat = fiat.equalsIgnoreCase("USD") || fiat.equalsIgnoreCase("EUR")
                ? fiat : "EUR";  // Ako traži nešto drugo, idi prvo u EUR

        TradeService cryptoToFiatRate = tradeCryptoRateRepository
                .findByCryptoAndFiat(crypto.toUpperCase(), intermediateFiat.toUpperCase())
                .orElseThrow(() -> new ExchangeRateNotFoundException(crypto, intermediateFiat));

        Double fiatAmount = quantity * cryptoToFiatRate.getRate();

        // 4. Ako je tražena valuta različita od USD/EUR, razmeni dalje
        if (!fiat.equalsIgnoreCase(intermediateFiat)) {
            ExchangeRateDTO fiatToFiatRate = currencyExchangeProxy.getExchangeRate(intermediateFiat, fiat);
            fiatAmount = fiatAmount * fiatToFiatRate.getRate();
        }

        // 5. Oduzmi crypto sa novčanika
        setBalanceForCrypto(wallet, crypto, currentCryptoBalance - quantity);
        cryptoWalletProxy.updateWallet(wallet.getId(), wallet);

        // 6. Dodaj fiat na bankovni račun
        BankAccountDTO account = bankAccountProxy.getAccountByEmail(email);
        Double currentFiatBalance = getBalanceForFiat(account, fiat);
        setBalanceForFiat(account, fiat, currentFiatBalance + fiatAmount);
        BankAccountDTO updatedAccount = bankAccountProxy.updateAccount(account.getId(), account);

        // 7. Kreiraj poruku
        String message = String.format(
                "Uspešno izvršena razmena %s: %.6f za %s: %.2f",
                crypto, quantity, fiat, fiatAmount
        );

        return ConversionResultDTO.builder()
                .email(email)
                .updatedBalance(updatedAccount)
                .message(message)
                .build();
    }

    // FIAT → CRYPTO (kupovina kripta)
    private ConversionResultDTO convertFiatToCrypto(String email, String fiat, String crypto, Double quantity) {
        // 1. Dobavi bankovni račun
        BankAccountDTO account = bankAccountProxy.getAccountByEmail(email);

        // 2. Proveri da li ima dovoljno fiat valute
        Double currentFiatBalance = getBalanceForFiat(account, fiat);
        if (currentFiatBalance < quantity) {
            throw new InsufficientFundsException(fiat, quantity, currentFiatBalance);
        }

        // 3. Prvo razmeni fiat → USD ili EUR (ako nije već)
        String intermediateFiat = fiat.equalsIgnoreCase("USD") || fiat.equalsIgnoreCase("EUR")
                ? fiat : "EUR";

        Double intermediateAmount = quantity;
        if (!fiat.equalsIgnoreCase(intermediateFiat)) {
            ExchangeRateDTO fiatToFiatRate = currencyExchangeProxy.getExchangeRate(fiat, intermediateFiat);
            intermediateAmount = quantity * fiatToFiatRate.getRate();
        }

        // 4. Razmeni USD/EUR → crypto
        TradeService fiatToCryptoRate = tradeCryptoRateRepository
                .findByCryptoAndFiat(crypto.toUpperCase(), intermediateFiat.toUpperCase())
                .orElseThrow(() -> new ExchangeRateNotFoundException(intermediateFiat, crypto));

        Double cryptoAmount = intermediateAmount / fiatToCryptoRate.getRate();

        // 5. Oduzmi fiat sa bankovnog računa
        setBalanceForFiat(account, fiat, currentFiatBalance - quantity);
        bankAccountProxy.updateAccount(account.getId(), account);

        // 6. Dodaj crypto na novčanik
        CryptoWalletDTO wallet = cryptoWalletProxy.getWalletByEmail(email);
        Double currentCryptoBalance = getBalanceForCrypto(wallet, crypto);
        setBalanceForCrypto(wallet, crypto, currentCryptoBalance + cryptoAmount);
        CryptoWalletDTO updatedWallet = cryptoWalletProxy.updateWallet(wallet.getId(), wallet);

        // 7. Kreiraj poruku
        String message = String.format(
                "Uspešno izvršena razmena %s: %.2f za %s: %.6f",
                fiat, quantity, crypto, cryptoAmount
        );

        return ConversionResultDTO.builder()
                .email(email)
                .updatedBalance(updatedWallet)
                .message(message)
                .build();
    }

    // --- POMOĆNE METODE ---

    private boolean isCrypto(String currency) {
        return currency.equalsIgnoreCase("BTC") ||
                currency.equalsIgnoreCase("ETH") ||
                currency.equalsIgnoreCase("USDT");
    }

    private boolean isFiat(String currency) {
        return currency.equalsIgnoreCase("EUR") ||
                currency.equalsIgnoreCase("USD") ||
                currency.equalsIgnoreCase("GBP") ||
                currency.equalsIgnoreCase("CHF") ||
                currency.equalsIgnoreCase("RSD");
    }

    private Double getBalanceForCrypto(CryptoWalletDTO wallet, String crypto) {
        return switch (crypto.toUpperCase()) {
            case "BTC" -> wallet.getBtc();
            case "ETH" -> wallet.getEth();
            case "USDT" -> wallet.getUsdt();
            default -> throw new IllegalArgumentException("Nepoznata crypto valuta: " + crypto);
        };
    }

    private void setBalanceForCrypto(CryptoWalletDTO wallet, String crypto, Double amount) {
        switch (crypto.toUpperCase()) {
            case "BTC" -> wallet.setBtc(amount);
            case "ETH" -> wallet.setEth(amount);
            case "USDT" -> wallet.setUsdt(amount);
            default -> throw new IllegalArgumentException("Nepoznata crypto valuta: " + crypto);
        }
    }

    private Double getBalanceForFiat(BankAccountDTO account, String fiat) {
        return switch (fiat.toUpperCase()) {
            case "EUR" -> account.getEur();
            case "USD" -> account.getUsd();
            case "GBP" -> account.getGbp();
            case "CHF" -> account.getChf();
            case "RSD" -> account.getRsd();
            default -> throw new IllegalArgumentException("Nepoznata fiat valuta: " + fiat);
        };
    }

    private void setBalanceForFiat(BankAccountDTO account, String fiat, Double amount) {
        switch (fiat.toUpperCase()) {
            case "EUR" -> account.setEur(amount);
            case "USD" -> account.setUsd(amount);
            case "GBP" -> account.setGbp(amount);
            case "CHF" -> account.setChf(amount);
            case "RSD" -> account.setRsd(amount);
            default -> throw new IllegalArgumentException("Nepoznata fiat valuta: " + fiat);
        }
    }
}
