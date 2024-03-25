package com.sajdakk.flipbook.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AddBookDto {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Genre is mandatory")
    private Integer genre_id;

    @NotBlank(message = "Language is mandatory")
    private Integer language_id;

    @NotBlank(message = "Date of publication is mandatory")
    private String date_of_publication;

    @NotBlank(message = "Page count is mandatory")
    private Integer page_count;

    @NotBlank(message = "Image is mandatory")
    private String image;

    @NotBlank(message = "ISBN number is mandatory")
    private String isbn_number;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Created by is mandatory")
    private Integer created_by;

    @NotBlank(message = "Authors are mandatory")
    private List<AuthorDto> authors;
}
