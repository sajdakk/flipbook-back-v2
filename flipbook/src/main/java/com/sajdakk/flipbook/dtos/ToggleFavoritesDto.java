package com.sajdakk.flipbook.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToggleFavoritesDto {
    @NotBlank(message = "Book id is mandatory")
    private int bookId;
}
