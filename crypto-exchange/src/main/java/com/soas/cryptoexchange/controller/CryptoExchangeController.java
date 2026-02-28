package com.soas.cryptoexchange.controller;

import com.soas.api.dto.ExchangeRateDTO;
import com.soas.cryptoexchange.service.CryptoExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crypto-exchange")
@RequiredArgsConstructor
public class CryptoExchangeController {

    private final CryptoExchangeService service;

    @GetMapping
    public ResponseEntity<List<ExchangeRateDTO>> getAllRates() {
        return ResponseEntity.ok(service.getAllRates());
    }

    @GetMapping("/{from}/{to}")
    public ResponseEntity<ExchangeRateDTO> getExchangeRate(
            @PathVariable String from,
            @PathVariable String to) {
        return ResponseEntity.ok(service.getExchangeRate(from, to));
    }
}
