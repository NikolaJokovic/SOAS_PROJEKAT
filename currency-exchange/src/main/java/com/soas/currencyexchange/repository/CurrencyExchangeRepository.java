package com.soas.currencyexchange.repository;


import com.soas.currencyexchange.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long>  {

    Optional<CurrencyExchange> findByFromCurrencyAndToCurrency(String fromCurrency,String toCurrency);
}
