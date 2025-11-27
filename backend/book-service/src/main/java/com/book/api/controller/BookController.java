package com.book.api.controller;

import com.book.api.dto.BookDto;
import com.book.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto,
                                              @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(bookService.createBook(bookDto,currentUserId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable UUID id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public ResponseEntity<BookDto> updateBook(@PathVariable UUID id,
                                              @RequestBody @Valid BookDto bookDto,
                                              @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(bookService.updateBook(id,bookDto,currentUserId));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> deleteBook(@PathVariable UUID id,
                                              @AuthenticationPrincipal Jwt jwt) {
        UUID currentUserId = UUID.fromString(jwt.getSubject());
        String role = jwt.getClaimAsString("role");
        bookService.deleteBookById(id,currentUserId,role);
        return ResponseEntity.noContent().build();
    }

}
