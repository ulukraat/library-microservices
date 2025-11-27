package com.book.api.factory;

import com.book.api.dto.BookDto;
import com.book.api.dto.UserDto;
import com.book.api.model.Book;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookDtoFactory {

    public BookDto makeBookDto(Book book,UserDto userDto) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .image(book.getImage())
                .author(userDto)
                .build();
    }
    public Book makeBook(BookDto bookDto, UUID authorId) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .description(bookDto.getDescription())
                .image(bookDto.getImage())
                .authorId(authorId)
                .build();

    }
}
