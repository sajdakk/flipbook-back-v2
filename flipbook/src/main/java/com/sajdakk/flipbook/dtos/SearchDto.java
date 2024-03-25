package com.sajdakk.flipbook.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchDto {

    private String title;
    private String name;
    private String surname;
}
