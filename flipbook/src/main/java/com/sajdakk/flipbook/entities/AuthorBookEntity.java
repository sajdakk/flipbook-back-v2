package com.sajdakk.flipbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajdakk.flipbook.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Entity
@Table(name = "author_book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class AuthorBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonIgnore
    private BookEntity book;
}
