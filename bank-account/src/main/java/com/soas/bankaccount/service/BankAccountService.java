package com.soas.bankaccount.service;

import com.soas.bankaccount.entity.BankAccount;
import com.soas.bankaccount.repository.BankAccountRepository;
import com.soas.api.dto.BankAccountDTO;
import com.soas.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository repository;


    public List<BankAccountDTO> getAllBankAccounts(){
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BankAccountDTO getAccountByEmail(String email) {
        BankAccount account = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return convertToDTO(account);
    }

    @Transactional
    public BankAccountDTO createAccount(BankAccountDTO dto) {
        BankAccount account = BankAccount.builder()
                .email(dto.getEmail())
                .eur(dto.getEur())
                .usd(dto.getUsd())
                .gbp(dto.getGbp())
                .chf(dto.getChf())
                .rsd(dto.getRsd())
                .build();
        return convertToDTO(repository.save(account));
    }

    @Transactional
    public BankAccountDTO updateAccount(Long id, BankAccountDTO dto) {
        BankAccount account = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        account.setEur(dto.getEur());
        account.setUsd(dto.getUsd());
        account.setGbp(dto.getGbp());
        account.setChf(dto.getChf());
        account.setRsd(dto.getRsd());
        return convertToDTO(repository.save(account));
    }

    @Transactional
    public void deleteAccountByEmail(String email) {
        repository.deleteByEmail(email);
    }

    private BankAccountDTO convertToDTO(BankAccount account) {
        return BankAccountDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .eur(account.getEur())
                .usd(account.getUsd())
                .gbp(account.getGbp())
                .chf(account.getChf())
                .rsd(account.getRsd())
                .build();
    }

    private BankAccount convertToEntity(BankAccountDTO dto){
        return BankAccount.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .eur(dto.getEur())
                .usd(dto.getUsd())
                .gbp(dto.getGbp())
                .chf(dto.getChf())
                .rsd(dto.getRsd())
                .build();
    }
}
