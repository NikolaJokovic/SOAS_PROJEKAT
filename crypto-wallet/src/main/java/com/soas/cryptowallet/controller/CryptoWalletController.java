package com.soas.cryptowallet.controller;

import com.soas.api.dto.CryptoWalletDTO;
import com.soas.cryptowallet.service.CryptoWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crypto-wallet")
@RequiredArgsConstructor
public class CryptoWalletController {

    private final CryptoWalletService service;

   @GetMapping({"/{id}"})
   public ResponseEntity<CryptoWalletDTO> getWalletById(@PathVariable Long id){
       return ResponseEntity.ok(service.getWalletById(id));
   }

    @GetMapping("/email/{email}")
    public ResponseEntity<CryptoWalletDTO> getWalletByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(service.getWalletByEmail(email));
    }

    @PostMapping
    public ResponseEntity<CryptoWalletDTO> createWallet(@RequestBody CryptoWalletDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createWallet(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CryptoWalletDTO> updateWallet(@PathVariable Long id, @RequestBody CryptoWalletDTO dto) {
        return ResponseEntity.ok(service.updateWallet(id, dto));
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteWallet(@PathVariable String email) {
        service.deleteWalletByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
