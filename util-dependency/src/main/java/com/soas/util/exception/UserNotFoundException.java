package com.soas.util.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) { super(message);}

    public UserNotFoundException(Long id) {
        super("Korisnik sa ID: " + id + " nije pronađen");
    }

    public UserNotFoundException(String email, boolean isEmail) {
        super("Korisnik sa email-om: " + email + " nije pronađen");
    }
}