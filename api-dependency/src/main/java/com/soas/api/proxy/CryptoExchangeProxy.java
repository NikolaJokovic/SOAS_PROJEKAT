package com.soas.api.proxy;

import com.soas.api.dto.ExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "crypto-exchange")
public interface CryptoExchangeProxy {

    @GetMapping("/crypto-exchange/{from}/{to}")
    ExchangeRateDTO getExchangeRate(
            @PathVariable String from,
            @PathVariable String to
    );
}