package com.example.atm.exception;

public class EmptySheetException extends RuntimeException {
    public EmptySheetException(String message) {
        super(message);
    }
}
