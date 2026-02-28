package com.soas.bankaccount.repository;

import com.soas.bankaccount.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByEmail(String email);

    void deleteByEmail(String email);

}
