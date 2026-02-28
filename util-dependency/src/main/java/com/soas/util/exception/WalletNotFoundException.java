package com.soas.util.exception;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(String email) {
        super("Crypto novčanik za email: " + email + " nije pronađen");
    }

    public WalletNotFoundException(Long id) {
        super("Crypto novčanik sa ID: " + id + " nije pronađen");
    }
}