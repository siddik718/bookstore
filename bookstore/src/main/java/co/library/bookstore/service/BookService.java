package co.library.bookstore.service;

import co.library.bookstore.exception.AlreadyExistException;
import co.library.bookstore.exception.InvalidDateException;
import co.library.bookstore.model.request.Init;
import co.library.bookstore.model.request.UpdateReq;
import co.library.bookstore.model.response.BookResponse;

import java.util.List;
import java.util.NoSuchElementException;

public interface BookService {
    BookResponse save(Init body) throws AlreadyExistException;
    List<BookResponse> book(String title, String author, int page, int size, String sortBy, String sortOrder) throws RuntimeException;
    List<BookResponse> bookByPublisher(Long publisherId, int page, int size, String sortBy, String sortOrder) throws RuntimeException;
    BookResponse book(Long id) throws NoSuchElementException;
    BookResponse update_book(Long id, UpdateReq body) throws NoSuchElementException, InvalidDateException;
    void delete_book(Long id) throws NoSuchElementException;
}
