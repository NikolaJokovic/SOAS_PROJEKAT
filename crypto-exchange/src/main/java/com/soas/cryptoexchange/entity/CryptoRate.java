package com.soas.cryptoexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crypto_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_crypto", nullable = false, length = 10)
    private String fromCrypto;

    @Column(name = "to_crypto", nullable = false, length = 10)
    private String toCrypto;

    @Column(nullable = false)
    private Double rate;

}
