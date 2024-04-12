package com.sajdakk.flipbook.models;

import com.sajdakk.flipbook.dtos.AddBookDto;
import com.sajdakk.flipbook.dtos.SearchDto;
import com.sajdakk.flipbook.entities.BookEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.repositories.BooksRepository;
import com.sajdakk.flipbook.services.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.util.*;

@Component
public class BooksModel {

    BooksRepository booksRepository;
    UploadService uploadService;

    public BooksModel(BooksRepository booksRepository, UploadService uploadService) {
        this.booksRepository = booksRepository;
        this.uploadService = uploadService;
    }

    public Integer addBook(AddBookDto addBookDto) {
        byte[] imageData = Base64.getDecoder().decode(addBookDto.getImage());

        String imagePath = uploadService.uploadFile(imageData, addBookDto.getImageExtension());
        if (imagePath == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }


        List<String> authors = new ArrayList<>();
        for (int i = 0; i < addBookDto.getAuthors().size(); i++) {
            String name = addBookDto.getAuthors().get(i).getName();
            String surname = addBookDto.getAuthors().get(i).getSurname();
            authors.add(name + ' ' + surname);
        }

        String authorsString = '{' + String.join(",", authors) + '}';
        return booksRepository.addBook(
                addBookDto.getTitle(),
                addBookDto.getGenre_id(),
                addBookDto.getLanguage_id(),
                addBookDto.getDate_of_publication(),
                addBookDto.getPage_count(),
                imagePath,
                addBookDto.getIsbn_number(),
                addBookDto.getDescription(),
                new Date().toString(),
                addBookDto.getCreated_by(),
                authorsString
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
        System.out.println(averages);
        result.sort((o1, o2) -> {
            return Double.compare(averages.get(o2.getId()), averages.get(o1.getId()));
        });

        return result;
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
