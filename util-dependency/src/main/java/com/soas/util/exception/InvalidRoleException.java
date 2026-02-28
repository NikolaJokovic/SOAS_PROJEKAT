package com.soas.util.exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }

    public InvalidRoleException() {
        super("Ne možete kreirati više od jednog OWNER korisnika");
    }
}
