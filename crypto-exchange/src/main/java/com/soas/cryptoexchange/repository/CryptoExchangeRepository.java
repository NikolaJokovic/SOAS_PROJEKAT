package com.soas.cryptoexchange.repository;

import com.soas.cryptoexchange.entity.CryptoRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoExchangeRepository extends JpaRepository<CryptoRate, Long> {

    Optional<CryptoRate> findByFromCryptoAndToCrypto(String fromCrypto, String toCrypto);
}
