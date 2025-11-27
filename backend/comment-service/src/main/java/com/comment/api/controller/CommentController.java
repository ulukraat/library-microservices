package com.comment.api.controller;

import com.comment.api.dto.CommentDto;
import com.comment.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    public final CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        UUID currentBookId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(commentService.createComment(commentDto, currentUserId, currentBookId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable UUID id) {
        CommentDto commentDto = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDto);
    }
    @GetMapping()
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> commentDto = commentService.getAllComments();
        return ResponseEntity.ok(commentDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable UUID id,
                                                    @RequestBody CommentDto commentDto,
                                                    @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        UUID currentBookId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(commentService.updateComment(id,commentDto,currentUserId, currentBookId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id,
                                              @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        UUID currentBookId = UUID.fromString(jwt.getSubject());
        String role = jwt.getClaimAsString("role");
        commentService.deleteCommentById(id,currentUserId,currentBookId,role);
        return ResponseEntity.noContent().build();
    }

}
