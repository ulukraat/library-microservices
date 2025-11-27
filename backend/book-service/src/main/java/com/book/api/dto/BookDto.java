package com.book.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private UUID id;
    private String title;
    private String description;
    private String image;
    private UserDto author;
}
