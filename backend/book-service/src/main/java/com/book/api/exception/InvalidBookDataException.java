package com.book.api.exception;

public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super("Invalid book data: " + message);
    }
}
