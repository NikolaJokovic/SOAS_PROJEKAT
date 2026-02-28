package com.soas.cryptowallet.repository;

import com.soas.cryptowallet.entity.CryptoWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {

    Optional<CryptoWallet> findByEmail(String email);

    void deleteByEmail(String email);
}
