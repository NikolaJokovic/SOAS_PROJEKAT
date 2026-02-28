package com.soas.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency", nullable = false, length = 3)
    private String fromCurrency;  // EUR, USD, GBP, CHF, RSD

    @Column(name = "to_currency", nullable = false, length = 3)
    private String toCurrency;    // EUR, USD, GBP, CHF, RSD

    @Column(nullable = false)
    private Double rate;           // Kurs razmene
}