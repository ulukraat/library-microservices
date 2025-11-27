package com.comment.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private UUID id;
    private String title;
    private String description;
    private String image;
    private UserDto author;

}
