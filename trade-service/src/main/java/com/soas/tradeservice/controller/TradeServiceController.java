package com.soas.tradeservice.controller;

import com.soas.api.dto.ConversionResultDTO;
import com.soas.tradeservice.service.TradeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade-service")
@RequiredArgsConstructor
public class TradeServiceController {

    private final TradeServiceService service;

    @GetMapping
    public ResponseEntity<ConversionResultDTO> trade(
            @RequestParam String email,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double quantity) {

        ConversionResultDTO result = service.trade(email, from, to, quantity);
        return ResponseEntity.ok(result);
    }
}
