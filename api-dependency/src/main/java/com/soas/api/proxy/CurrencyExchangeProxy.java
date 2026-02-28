package com.soas.api.proxy;

import com.soas.api.dto.ExchangeRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/{from}/{to}")
    ExchangeRateDTO getExchangeRate(
            @PathVariable String from,
            @PathVariable String to
    );
}