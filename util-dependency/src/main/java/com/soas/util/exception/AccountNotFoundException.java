package com.soas.util.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String email) {
        super("Bankovni račun za email: " + email + " nije pronađen");
    }

    public AccountNotFoundException(Long id) {
        super("Bankovni račun sa ID: " + id + " nije pronađen");
    }


}
