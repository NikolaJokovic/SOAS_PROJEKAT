package com.soas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO {

    private Long id;
    private String fromCurrency;  // EUR
    private String toCurrency;    // USD
    private Double rate;          // 1.10

    // Pomoćna metoda za konverziju
    public Double convert(Double amount) {
        return amount * rate;
    }
}