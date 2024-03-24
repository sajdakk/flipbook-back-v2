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
@Table(name = "languages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String language;

    @OneToMany(mappedBy = "language")
    @JsonIgnore
    private List<BookEntity> books;
}
