package com.book.api.exception;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String title) {
        super("Book already exists: " + title);
    }
}
