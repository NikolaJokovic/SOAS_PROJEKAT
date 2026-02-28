package com.soas.tradeservice.repository;


import com.soas.tradeservice.entity.TradeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface TradeServiceRepository extends JpaRepository<TradeService, Long>{

    Optional<TradeService> findByCryptoAndFiat(String crypto, String fiat);
}
