package com.comment.api.service;

import com.comment.api.dto.BookDto;
import com.comment.api.dto.CommentDto;
import com.comment.api.dto.UserDto;
import com.comment.api.factory.CommentDtoFactory;
import com.comment.api.model.Comment;
import com.comment.api.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final WebClient webClient;
    private final CommentDtoFactory commentDtoFactory;

    public CommentDto createComment(CommentDto commentDto, UUID currentUserId,UUID currentBookId) {
        Comment comment = commentDtoFactory.makeComment(commentDto,currentUserId,currentBookId);

        Comment savedComment = commentRepository.save(comment);

        UserDto userDto = getUser(currentUserId);
        BookDto bookDto = getBook(currentBookId);

        return commentDtoFactory.makeCommentDto(savedComment,userDto,bookDto);
    }

    public CommentDto updateComment(UUID id,CommentDto commentDto, UUID currentUserId, UUID currentBookId) {

        Comment comment = commentRepository.findById(id).orElseThrow(null);

        comment.setText(commentDto.getText());
        comment.setCreatedAt(commentDto.getCreatedAt());

        Comment savedComment = commentRepository.save(comment);
        UserDto userDto = getUser(currentUserId);
        BookDto bookDto = getBook(currentBookId);
        return commentDtoFactory.makeCommentDto(savedComment,userDto,bookDto);
    }

    public CommentDto getCommentById(UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(null);
        UserDto userDto = getUser(comment.getUserId());
        BookDto bookDto = getBook(comment.getBookId());

        return commentDtoFactory.makeCommentDto(comment,userDto,bookDto);
    }

    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(comment -> {
                    UserDto userDto = getUser(comment.getUserId());
                    BookDto bookDto = getBook(comment.getBookId());
                    return  commentDtoFactory.makeCommentDto(comment,userDto,bookDto);
                })
                .toList();
    }

    public void deleteCommentById(UUID commentId, UUID currentUserId, UUID currentBookId,String role) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(null);
        checkDeleteRole(comment,currentUserId,currentBookId,role);

        commentRepository.deleteById(commentId);
    }

    public UserDto getUser(UUID currentUserId) {
        return webClient.get()
                .uri("http://api/users/profile/{id}",currentUserId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
    public BookDto getBook(UUID currentBookId) {
        return webClient.get()
                .uri("http://api/books/profile/{id}",currentBookId)
                .retrieve()
                .bodyToMono(BookDto.class)
                .block();
    }
    private void checkDeleteRole(Comment comment,UUID currentUserId,UUID currentBookId,String role) {
        if(!comment.getUserId().equals(currentUserId) && !comment.getBookId().equals(currentBookId) || !"ADMIN".equals(role)) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }
    }
}
