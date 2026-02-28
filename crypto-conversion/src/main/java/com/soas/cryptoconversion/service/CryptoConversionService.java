package com.soas.cryptoconversion.service;

import com.soas.api.dto.ConversionResultDTO;
import com.soas.api.dto.CryptoWalletDTO;
import com.soas.api.dto.ExchangeRateDTO;
import com.soas.api.proxy.CryptoExchangeProxy;
import com.soas.api.proxy.CryptoWalletProxy;
import com.soas.util.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoConversionService {

   private final CryptoExchangeProxy cryptoExchangeProxy;
   private final CryptoWalletProxy cryptoWalletProxy;

    public ConversionResultDTO convert(String email, String from, String to, Double quantity) {
        ExchangeRateDTO exchangeRate = cryptoExchangeProxy.getExchangeRate(from, to);

        CryptoWalletDTO wallet = cryptoWalletProxy.getWalletByEmail(email);

        Double currentBalance = getBalanceForCrypto(wallet, from);
        if (currentBalance < quantity) {
            throw new InsufficientFundsException(from, quantity, currentBalance);
        }

        Double convertedAmount = exchangeRate.convert(quantity);

        setBalanceForCrypto(wallet, from, currentBalance - quantity);
        Double newToBalance = getBalanceForCrypto(wallet, to) + convertedAmount;
        setBalanceForCrypto(wallet, to, newToBalance);

        CryptoWalletDTO updatedWallet = cryptoWalletProxy.updateWallet(wallet.getId(), wallet);

        String message = String.format(
                "Uspešno izvršena razmena %s: %.6f za %s: %.6f",
                from, quantity, to, convertedAmount
        );

        return ConversionResultDTO.builder()
                .email(email)
                .updatedBalance(updatedWallet)
                .message(message)
                .build();
    }

    private Double getBalanceForCrypto(CryptoWalletDTO wallet, String crypto) {
        return switch (crypto.toUpperCase()) {
            case "BTC" -> wallet.getBtc();
            case "ETH" -> wallet.getEth();
            case "USDT" -> wallet.getUsdt();
            default -> throw new IllegalArgumentException("Nepoznata kripto valuta: " + crypto);
        };
    }

    private void setBalanceForCrypto(CryptoWalletDTO wallet, String crypto, Double amount) {
        switch (crypto.toUpperCase()) {
            case "BTC" -> wallet.setBtc(amount);
            case "ETH" -> wallet.setEth(amount);
            case "USDT" -> wallet.setUsdt(amount);
            default -> throw new IllegalArgumentException("Nepoznata kripto valuta: " + crypto);
        }
    }
}
