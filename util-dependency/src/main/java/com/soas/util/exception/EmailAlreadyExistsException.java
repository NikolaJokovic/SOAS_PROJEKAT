package com.soas.util.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Korisnik sa email-om " + email + " već postoji u sistemu");
    }
}