package com.soas.currencyexchange.service;


import com.soas.api.dto.ExchangeRateDTO;
import com.soas.currencyexchange.entity.CurrencyExchange;
import com.soas.currencyexchange.repository.CurrencyExchangeRepository;
import com.soas.util.exception.ExchangeRateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    private final CurrencyExchangeRepository repo;

    public ExchangeRateDTO getExchangeRate(String from,String to){
        String fromUpper= from.toUpperCase();
        String toUpper= to.toUpperCase();

        CurrencyExchange rate = repo.findByFromCurrencyAndToCurrency(fromUpper,toUpper)
                .orElseThrow(()-> new ExchangeRateNotFoundException(fromUpper,toUpper));
        return convertToDto(rate);
    }

    public List<ExchangeRateDTO> getAllRates() {
        return repo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ExchangeRateDTO convertToDto(CurrencyExchange rate) {
        return ExchangeRateDTO.builder()
                .id(rate.getId())
                .fromCurrency(rate.getFromCurrency())
                .toCurrency(rate.getToCurrency())
                .rate(rate.getRate())
                .build();
    }
}
