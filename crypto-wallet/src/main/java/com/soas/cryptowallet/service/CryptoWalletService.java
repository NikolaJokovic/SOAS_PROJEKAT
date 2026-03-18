package com.soas.cryptowallet.service;
import com.soas.api.dto.CryptoWalletDTO;
import com.soas.cryptowallet.entity.CryptoWallet;
import com.soas.cryptowallet.repository.CryptoWalletRepository;
import com.soas.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CryptoWalletService {

    private final CryptoWalletRepository repository;

    public CryptoWalletDTO getWalletByEmail(String email) {
        CryptoWallet wallet = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email, true));
        return convertToDTO(wallet);
    }

    @Transactional
    public CryptoWalletDTO createWallet(CryptoWalletDTO dto) {
        CryptoWallet wallet = CryptoWallet.builder()
                .email(dto.getEmail())
                .btc(dto.getBtc())
                .eth(dto.getEth())
                .usdt(dto.getUsdt())
                .build();
        return convertToDTO(repository.save(wallet));
    }

    @Transactional
    public CryptoWalletDTO updateWallet(Long id, CryptoWalletDTO dto) {
        CryptoWallet wallet = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        wallet.setBtc(dto.getBtc());
        wallet.setEth(dto.getEth());
        wallet.setUsdt(dto.getUsdt());
        return convertToDTO(repository.save(wallet));
    }

    @Transactional
    public void deleteWalletByEmail(String email) {
        repository.deleteByEmail(email);
    }

    private CryptoWalletDTO convertToDTO(CryptoWallet wallet) {
        return CryptoWalletDTO.builder()
                .id(wallet.getId())
                .email(wallet.getEmail())
                .btc(wallet.getBtc())
                .eth(wallet.getEth())
                .usdt(wallet.getUsdt())
                .build();
    }

    @Transactional
    public CryptoWalletDTO getWalletById(Long id) {
        CryptoWallet wallet = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found:" + id));
        return convertToDTO(wallet);
    }

    private CryptoWallet convertToEnum(CryptoWalletDTO dto){
        return CryptoWallet.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .btc(dto.getBtc())
                .eth(dto.getEth())
                .usdt(dto.getUsdt())
                .build();
    }
}