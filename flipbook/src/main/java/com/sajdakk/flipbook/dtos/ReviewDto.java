package com.sajdakk.flipbook.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewDto {
    @NotBlank(message = "User id is mandatory")
    private Integer userId;

    @NotBlank(message = "Book id is mandatory")
    private Integer bookId;

    @NotBlank(message = "Content is mandatory")
    private String content;

    @NotBlank(message = "Rate is mandatory")
    private Integer rate;
}
