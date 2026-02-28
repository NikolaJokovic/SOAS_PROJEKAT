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
public class BankAccountDTO {

    private Long id;

    @NotBlank(message = "email je obavezan")
    @Email(message = "email mora biti validan")
    private String email;

    @PositiveOrZero(message = "EUR ne može biti negativan")
    private Double eur = 0.0;

    @PositiveOrZero(message = "USD ne može biti negativan")
    private Double usd = 0.0;

    @PositiveOrZero(message = "GBP ne može biti negativan")
    private Double gbp = 0.0;

    @PositiveOrZero(message = "CHF ne može biti negativan")
    private Double chf = 0.0;

    @PositiveOrZero(message = "RSD ne može biti negativan")
    private Double rsd = 0.0;
}