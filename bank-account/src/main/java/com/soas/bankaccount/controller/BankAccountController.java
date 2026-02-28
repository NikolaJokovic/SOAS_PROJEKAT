package com.soas.bankaccount.controller;

import com.soas.api.dto.BankAccountDTO;
import com.soas.bankaccount.service.BankAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts(){
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<BankAccountDTO> getAccountByEmail(@PathVariable String email) {
        return ResponseEntity.ok(bankAccountService.getAccountByEmail(email));
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@RequestBody BankAccountDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.createAccount(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateAccount(@PathVariable Long id, @RequestBody BankAccountDTO dto) {
        return ResponseEntity.ok(bankAccountService.updateAccount(id, dto));
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteAccountByEmail(@PathVariable String email) {
        bankAccountService.deleteAccountByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
