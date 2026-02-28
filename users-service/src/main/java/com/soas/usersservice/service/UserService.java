package com.soas.usersservice.service;

import com.soas.api.dto.BankAccountDTO;
import com.soas.api.dto.CryptoWalletDTO;
import com.soas.api.dto.UserDTO;
import com.soas.api.enums.Role;
import com.soas.api.proxy.BankAccountProxy;
import com.soas.api.proxy.CryptoWalletProxy;
import com.soas.usersservice.entity.User;
import com.soas.usersservice.repository.UserRepository;
import com.soas.util.exception.EmailAlreadyExistsException;
import com.soas.util.exception.InvalidRoleException;
import com.soas.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BankAccountProxy bankAccountProxy;
    private final CryptoWalletProxy cryptoWalletProxy;

    // Pronađi sve korisnike
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // Pronađi korisnika po ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToDTO(user);
    }

    // Pronađi korisnika po email-u
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email, true));
        return convertToDTO(user);
    }

    // Kreiraj novog korisnika
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        // Provera: Da li email već postoji?
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }

        // Provera: Da li pokušavamo da kreiramo drugog OWNER-a?
        if (userDTO.getRole() == Role.OWNER && userRepository.existsByRole(Role.OWNER)) {
            throw new InvalidRoleException();
        }

        // Sačuvaj korisnika
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);

        // Ako je USER, automatski kreiraj Bank Account i Crypto Wallet
        if (savedUser.getRole() == Role.USER) {
            createBankAccountForUser(savedUser.getEmail());
            createCryptoWalletForUser(savedUser.getEmail());
        }

        return convertToDTO(savedUser);
    }

    // Ažuriraj korisnika
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Provera: Da li novi email već postoji kod drugog korisnika?
        if (!existingUser.getEmail().equals(userDTO.getEmail())
                && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }

        // Provera: Da li menjamo ulogu na OWNER a već postoji OWNER?
        if (userDTO.getRole() == Role.OWNER
                && existingUser.getRole() != Role.OWNER
                && userRepository.existsByRole(Role.OWNER)) {
            throw new InvalidRoleException();
        }

        // Provera: Ako menjamo ulogu sa USER na nešto drugo, obriši račune
        if (existingUser.getRole() == Role.USER && userDTO.getRole() != Role.USER) {
            deleteBankAccountForUser(existingUser.getEmail());
            deleteCryptoWalletForUser(existingUser.getEmail());
        }

        // Provera: Ako menjamo ulogu NA USER, kreiraj račune
        if (existingUser.getRole() != Role.USER && userDTO.getRole() == Role.USER) {
           createBankAccountForUser(userDTO.getEmail());
           createCryptoWalletForUser(userDTO.getEmail());
        }

        // Ažuriraj podatke
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    // Obriši korisnika
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Ako je USER, prvo obriši Bank Account i Crypto Wallet
        if (user.getRole() == Role.USER) {
            deleteBankAccountForUser(user.getEmail());
            deleteCryptoWalletForUser(user.getEmail());
        }

        userRepository.delete(user);
    }

    // --- POMOĆNE METODE ---

    private void createBankAccountForUser(String email) {
        try {
            BankAccountDTO account = BankAccountDTO.builder()
                    .email(email)
                    .eur(0.0)
                    .usd(0.0)
                    .gbp(0.0)
                    .chf(0.0)
                    .rsd(0.0)
                    .build();
            bankAccountProxy.createAccount(account);
        } catch (Exception e) {
            // Loguj grešku ali ne zaustavljaj kreiranje korisnika
            System.err.println("Greška prilikom kreiranja bankovnog računa: " + e.getMessage());
        }
    }

    private void deleteBankAccountForUser(String email) {
        try {
            bankAccountProxy.deleteAccountByEmail(email);
        } catch (Exception e) {
            System.err.println("Greška prilikom brisanja bankovnog računa: " + e.getMessage());
        }
    }
    private void createCryptoWalletForUser(String email) {
        try {
            CryptoWalletDTO wallet = CryptoWalletDTO.builder()
                    .email(email)
                    .btc(0.0)
                    .eth(0.0)
                    .usdt(0.0)
                    .build();
            cryptoWalletProxy.createWallet(wallet);
        } catch (Exception e) {
            System.err.println("Greška prilikom kreiranja crypto novčanika: " + e.getMessage());
        }
    }



    private void deleteCryptoWalletForUser(String email) {
        try {
            cryptoWalletProxy.deleteWalletByEmail(email);
        } catch (Exception e) {
            System.err.println("Greška prilikom brisanja crypto novčanika: " + e.getMessage());
        }
    }

    // Konverzija Entity → DTO
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    // Konverzija DTO → Entity
    private User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }
}