package com.soas.cryptoexchange.service;

import com.soas.api.dto.ExchangeRateDTO;
import com.soas.cryptoexchange.entity.CryptoRate;
import com.soas.cryptoexchange.repository.CryptoExchangeRepository;
import com.soas.util.exception.ExchangeRateNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoExchangeService {

    private final CryptoExchangeRepository repository;

    public ExchangeRateDTO getExchangeRate(String from, String to) {
        String fromUpper = from.toUpperCase();
        String toUpper = to.toUpperCase();

        CryptoRate rate = repository.findByFromCryptoAndToCrypto(fromUpper, toUpper)
                .orElseThrow(() -> new ExchangeRateNotFoundException(fromUpper, toUpper));

        return convertToDTO(rate);
    }

    public List<ExchangeRateDTO> getAllRates() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ExchangeRateDTO convertToDTO(CryptoRate rate) {
        return ExchangeRateDTO.builder()
                .id(rate.getId())
                .fromCurrency(rate.getFromCrypto())
                .toCurrency(rate.getToCrypto())
                .rate(rate.getRate())
                .build();
    }
}