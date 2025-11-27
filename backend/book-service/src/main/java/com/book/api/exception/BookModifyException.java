package com.book.api.exception;

import java.util.UUID;

public class BookModifyException extends RuntimeException {
    public BookModifyException() {
        super("You don't have permission to modify this book");
    }
    public BookModifyException(UUID userId, UUID bookId) {
        super("User " + userId + "does not have permission to modify book" + bookId );
    }
}
