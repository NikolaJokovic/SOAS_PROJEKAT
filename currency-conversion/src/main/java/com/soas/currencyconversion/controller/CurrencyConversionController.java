package com.soas.currencyconversion.controller;

import com.soas.api.dto.ConversionResultDTO;
import com.soas.currencyconversion.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency-conversion")
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyConversionService service;

    @GetMapping
    public ResponseEntity<ConversionResultDTO> convertCurrency(
            @RequestParam String email,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double quantity) {

        ConversionResultDTO result = service.convert(email, from, to, quantity);
        return ResponseEntity.ok(result);
    }
}