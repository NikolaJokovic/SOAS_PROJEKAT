package com.soas.cryptoconversion.controller;

import com.soas.api.dto.ConversionResultDTO;
import com.soas.cryptoconversion.service.CryptoConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crypto-conversion")
@RequiredArgsConstructor
public class CryptoConversionController {

    private final CryptoConversionService service;

    @GetMapping
    public ResponseEntity<ConversionResultDTO> convertCrypto(
            @RequestParam String email,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double quantity) {

        ConversionResultDTO result = service.convert(email, from, to, quantity);
        return ResponseEntity.ok(result);
    }
}
