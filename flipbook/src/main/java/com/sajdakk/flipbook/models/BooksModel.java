package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.AddBookDto;
import com.sajdakk.flipbook.dtos.AuthorDto;
import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.*;
import com.sajdakk.flipbook.repositories.*;
import com.sajdakk.flipbook.services.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Component
public class BooksModel {

    BooksRepository booksRepository;

    UploadService uploadService;

    AuthorsRepository authorsRepository;

    public BooksModel(BooksRepository booksRepository, AuthorsRepository authorsRepository, UploadService uploadService) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;

        this.uploadService = uploadService;
    }

    public Integer addBook(AddBookDto addBookDto) {
        byte[] imageData = Base64.getDecoder().decode(addBookDto.getImage());

        String imagePath = uploadService.uploadFile(imageData, addBookDto.getImageExtension());
        if (imagePath == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }

        List<Integer> bookAuthors = new ArrayList<>();
        List<AuthorDto> authorsDto = addBookDto.getAuthors();
        for (AuthorDto authorDto : authorsDto) {
            Integer id = authorDto.getId();
            System.out.println(id);
            if (id == null) {
                AuthorEntity author = AuthorEntity.builder()
                        .name(authorDto.getName())
                        .surname(authorDto.getSurname())
                        .build();
                System.out.println(author);
                AuthorEntity result = authorsRepository.save(author);
                System.out.println(result);
                bookAuthors.add(result.getId());
                continue;
            }

            AuthorEntity author = authorsRepository.findById(authorDto.getId()).orElse(null);
            if (author == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
            }

            bookAuthors.add(author.getId());
        }

        System.out.println(bookAuthors);
        System.out.println("Dodajemy ksiazke!!!!");
        Integer[] authorsArray = new Integer[bookAuthors.size()];
        authorsArray = bookAuthors.toArray(authorsArray);

        Date dateOfPub;
        dateOfPub =  Date.from(Instant.parse(addBookDto.getDate_of_publication()));

        return booksRepository.addBook(
                addBookDto.getTitle(),
                addBookDto.getGenre_id(),
                addBookDto.getLanguage_id(),
                dateOfPub,
                addBookDto.getPage_count(),
                imagePath,
                addBookDto.getIsbn_number(),
                addBookDto.getDescription(),
                new Date(),
                addBookDto.getCreated_by(),
                authorsArray

        );


    }

    public List<BookEntity> getAllBooks() {
        List<BookEntity> result = booksRepository.findAll();
        HashMap<Integer, Double> averages = new HashMap<>();
        for (BookEntity book : result) {
            double sum = 0;
            for (ReviewEntity review : book.getReviews()) {
                sum += review.getRate();
            }

            if (book.getReviews().isEmpty()) {
                averages.put(book.getId(), 0.0);
                continue;
            }


            averages.put(book.getId(), sum / book.getReviews().size());

        }
        result.sort((o1, o2) -> {
            return Double.compare(averages.get(o2.getId()), averages.get(o1.getId()));
        });

        return result;
    }

    public List<BookEntity> getFavorites(int userId) {
        Collection<BookEntity> result = booksRepository.getFavorites(userId);
        return new ArrayList<BookEntity>(result);

    }

    public List<BookEntity> getForAdmin() {
        Collection<BookEntity> result = booksRepository.findForAdmin();
        return new ArrayList<BookEntity>(result);

    }

    public List<BookEntity> search(SearchDto searchDto) {
        Collection<BookEntity> books = booksRepository.findByTitleOrAuthorNameOrAuthorSurname(
                searchDto.getTitle(),
                searchDto.getName(),
                searchDto.getSurname()
        );
        return new ArrayList<BookEntity>(books);
    }


    public BookEntity getBookById(Integer id) {
        return booksRepository.findById(id).orElse(null);
    }

    public void acceptBook(Integer id) {
        BookEntity book = booksRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Timestamp now = new Timestamp(new Date().getTime());
        book.setAcceptDate(now);
        booksRepository.save(book);
    }

    public void rejectBook(Integer id) {
        BookEntity book = booksRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Timestamp now = new Timestamp(new Date().getTime());
        book.setRejectDate(now);
        booksRepository.save(book);
    }
}
