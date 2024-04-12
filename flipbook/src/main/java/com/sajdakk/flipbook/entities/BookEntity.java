package com.sajdakk.flipbook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "books")
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "date_of_publication")
    private Timestamp dateOfPublication;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "image")
    private String image;

    @Column(name = "isbn_number")
    private String isbnNumber;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "upload_date")
    private Timestamp uploadDate;

    @Column(name = "accept_date")
    private Timestamp acceptDate;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private UserEntity createdBy;

    @Column(name = "reject_date")
    private Timestamp rejectDate;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private GenreEntity genre;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private LanguageEntity language;

    @OneToMany(mappedBy = "book")
    private List<AuthorBookEntity> authorBooks;

    @OneToMany(mappedBy = "book")
    private List<FavoriteEntity> favorites;

    @OneToMany(mappedBy = "book")
    private List<ReviewEntity> reviews;
}
