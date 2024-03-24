package com.sajdakk.flipbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Entity
@Table(name = "genres")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<BookEntity> books;
}
