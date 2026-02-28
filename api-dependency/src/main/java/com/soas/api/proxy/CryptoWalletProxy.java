package com.soas.api.proxy;

import com.soas.api.dto.CryptoWalletDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "crypto-wallet")
public interface CryptoWalletProxy {

    @GetMapping("/crypto-wallet/email/{email}")
    CryptoWalletDTO getWalletByEmail(@PathVariable String email);

    @PostMapping("/crypto-wallet")
    CryptoWalletDTO createWallet(@RequestBody CryptoWalletDTO walletDTO);

    @PutMapping("/crypto-wallet/{id}")
    CryptoWalletDTO updateWallet(@PathVariable Long id, @RequestBody CryptoWalletDTO walletDTO);

    @DeleteMapping("/crypto-wallet/email/{email}")
    void deleteWalletByEmail(@PathVariable String email);
}