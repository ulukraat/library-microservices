package com.comment.api.factory;

import com.comment.api.dto.BookDto;
import com.comment.api.dto.CommentDto;
import com.comment.api.dto.UserDto;
import com.comment.api.model.Comment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentDtoFactory {
    public CommentDto makeCommentDto(Comment comment, UserDto userDto, BookDto bookDto) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .user(userDto)
                .book(bookDto)
                .build();
    }
    public Comment makeComment(CommentDto commentDto, UUID userId, UUID bookId) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .createdAt(commentDto.getCreatedAt())
                .userId(userId)
                .bookId(bookId)
                .build();
    }
}
