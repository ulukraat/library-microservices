package com.comment.api.exception;

import java.util.UUID;

public class CommentModifyException extends RuntimeException {
    public CommentModifyException() {
        super("You don't have permission to modify this comment");
    }
    public CommentModifyException(UUID userId, UUID commentId) {
        super("User" + userId + "does not have permission to modify this comment" + commentId);
    }
}
