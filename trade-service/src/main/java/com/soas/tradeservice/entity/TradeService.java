package com.soas.tradeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trade_crypto_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crypto", nullable = false, length = 4)
    private String crypto;  // BTC, ETH, USDT

    @Column(name = "fiat", nullable = false, length = 3)
    private String fiat;    // EUR, USD

    @Column(nullable = false)
    private Double rate;
}
