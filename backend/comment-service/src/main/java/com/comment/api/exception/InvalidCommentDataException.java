package com.comment.api.exception;

public class InvalidCommentDataException extends RuntimeException {
    public InvalidCommentDataException(String message) {
        super("Invalid comment data: " + message);
    }
}
