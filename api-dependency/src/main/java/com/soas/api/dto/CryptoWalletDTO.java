package com.soas.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoWalletDTO {

    private Long id;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email mora biti validan")
    private String email;

    @PositiveOrZero(message = "BTC ne može biti negativan")
    private Double btc = 0.0;

    @PositiveOrZero(message = "ETH ne može biti negativan")
    private Double eth = 0.0;

    @PositiveOrZero(message = "USDT ne može biti negativan")
    private Double usdt = 0.0;
}