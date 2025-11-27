package com.book.api.service;

import com.book.api.dto.BookDto;
import com.book.api.dto.UserDto;
import com.book.api.exception.BookNotFoundException;
import com.book.api.exception.UnauthorizedBookAccessException;
import com.book.api.factory.BookDtoFactory;
import com.book.api.model.Book;
import com.book.api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final WebClient webClient;
    private final BookDtoFactory bookDtoFactory;

    public BookDto createBook(BookDto bookDto, UUID currentUserId) {
        Book book = bookDtoFactory.makeBook(bookDto, currentUserId);

        Book saved = bookRepository.save(book);

        UserDto userDto = getUser(currentUserId);
        return bookDtoFactory.makeBookDto(saved, userDto);
    }

    public BookDto updateBook(UUID id, BookDto bookDto, UUID currentUserId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        checkUpdateRole(book, currentUserId);

        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setImage(bookDto.getImage());

        Book saved = bookRepository.save(book);
        UserDto userDto = getUser(currentUserId);
        return bookDtoFactory.makeBookDto(saved, userDto);
    }

    public BookDto getBookById(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        UserDto userDto = getUser(book.getAuthorId());

        return bookDtoFactory.makeBookDto(book, userDto);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> {
                    UserDto userDto = getUser(book.getAuthorId());
                    return bookDtoFactory.makeBookDto(book, userDto);
                })
                .toList(); //здесь по оптимизации будет N запросы ,в будущем думаю как оптимизировать
    }

    public void deleteBookById(UUID bookId, UUID currentUserId, String role) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        checkDeleteRole(book, currentUserId, role);

        bookRepository.delete(book);
    }

    private UserDto getUser(UUID currentUserId) {
        return webClient.get()
                .uri("http://api/users/profile/{id}", currentUserId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    private void checkUpdateRole(Book book, UUID currentUserId) {
        if (!book.getAuthorId().equals(currentUserId)) {
            throw new UnauthorizedBookAccessException(currentUserId, book.getId());
        }
    }

    private void checkDeleteRole(Book book, UUID currentUserId, String role) {
        if (!book.getAuthorId().equals(currentUserId) && !"ADMIN".equals(role)) {
            throw new UnauthorizedBookAccessException(currentUserId, book.getId());
        }
    }
}