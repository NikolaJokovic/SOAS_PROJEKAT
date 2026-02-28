package com.soas.api.proxy;

import com.soas.api.dto.BankAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="bank-account")
public interface BankAccountProxy {

    @GetMapping("/bank-account/email/{email}")
    BankAccountDTO getAccountByEmail(@PathVariable("email") String email);

    @PostMapping("/bank-account")
    BankAccountDTO createAccount(@RequestBody BankAccountDTO accountDTO);

    @PutMapping("/bank-account/{id}")
    BankAccountDTO updateAccount(@PathVariable("id") Long id, @RequestBody BankAccountDTO accountDTO);

    @DeleteMapping("/bank-account/email/{email}")
    void deleteAccountByEmail(@PathVariable("email") String email);

}
