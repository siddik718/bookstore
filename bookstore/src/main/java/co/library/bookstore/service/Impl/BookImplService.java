package co.library.bookstore.service.Impl;

import co.library.bookstore.exception.AlreadyExistException;
import co.library.bookstore.exception.InvalidDateException;
import co.library.bookstore.model.entity.Book;
import co.library.bookstore.model.request.Init;
import co.library.bookstore.model.request.UpdateReq;
import co.library.bookstore.model.response.BookResponse;
import co.library.bookstore.repository.BookRepository;
import co.library.bookstore.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookImplService implements BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileUploaderImplService fileUploaderService;

    @Override
    public BookResponse save(Init body) throws AlreadyExistException {
        Book book = this.repository.findByTitle(body.getTitle());
        if (book != null) {
            throw new AlreadyExistException("Title is already taken");
        }

        String bookUrl = fileUploaderService.upload(body.getBook());
        String bookCoverImageUrl = fileUploaderService.upload(body.getBookCoverImage());

        Book newBook = this.mapper.map(body, Book.class);
        newBook.setBooksRemoteUrl(bookUrl);
        newBook.setBooksCoverImageUrl(bookCoverImageUrl);

        Book savedBook = this.repository.save(newBook);
        return this.mapper.map(savedBook, BookResponse.class);
    }

    @Override
    public List<BookResponse> book(String title, String author, int page, int size, String sortBy, String sortOrder) throws RuntimeException {
        Sort sort = Sort.by(sortBy != null ? sortBy : "id");
        sort = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books;

        if(title != null && author != null) {
            books = repository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author, pageable);
        }else if(title != null) {
            books = repository.findByTitleContainingIgnoreCase(title,pageable);
        }else if(author != null) {
            books = repository.findByAuthorContainingIgnoreCase(author, pageable);
        }else {
            books = repository.findAll(pageable);
        }

        return books.stream()
                .map(book -> mapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse book(Long id) throws NoSuchElementException {
        Book book = this.findByBookId(id);
        return this.mapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse update_book(Long id, UpdateReq body) throws NoSuchElementException, InvalidDateException{
        final String newTitle = body.getTitle();
        final String newAuthor = body.getAuthor();
        final String newDesc = body.getShortDescription();
        final Date newFirst = body.getFirstPublication();
        final Date newLast = body.getLastPublication();

        Book book = this.findByBookId(id);
        if(newTitle != null && !newTitle.trim().isEmpty()) {
            book.setTitle(newTitle);
        }
        if(newAuthor != null && !newAuthor.trim().isEmpty()) {
            book.setAuthor(newAuthor);
        }
        if(newDesc != null && !newDesc.trim().isEmpty()) {
            book.setShortDescription(newDesc);
        }
        if(newLast != null) {
            if(newLast.after(new Date())) {
                throw new InvalidDateException("LastPublication Can't be in future!!!");
            }
            book.setLastPublication(newLast);
        }
        if(newFirst != null) {
            if (newFirst.after(book.getLastPublication())) {
                throw new InvalidDateException("FirstPublication Can't be After LastPublication");
            }
            book.setFirstPublication(newFirst);
        }

        Book updatedBook = this.repository.save(book);
        return this.mapper.map(updatedBook, BookResponse.class);
    }

    @Override
    public void delete_book(Long id) throws NoSuchElementException {
        Book book = this.findByBookId(id);
        this.repository.delete(book);
    }

    private Book findByBookId(Long Id) throws NoSuchElementException{
        Optional<Book> book = this.repository.findById(Id);
        if (book.isEmpty()) {
            throw new NoSuchElementException("Invalid Id");
        }
        return this.mapper.map(book, Book.class);
    }

    @Override
    public List<BookResponse> bookByPublisher(Long publisherId, int page, int size, String sortBy, String sortOrder)
            throws RuntimeException {
        Sort sort = Sort.by(sortBy != null ? sortBy : "id");
        sort = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page,size);
        Page<Book> books;
        books = repository.findByPublisherId(publisherId, pageable);
        return books.stream()
                .map(book -> mapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
    }

}
