package co.library.bookstore.controller;

import co.library.bookstore.exception.AlreadyExistException;
import co.library.bookstore.exception.InvalidDateException;
import co.library.bookstore.model.request.Init;
import co.library.bookstore.model.request.UpdateReq;
import co.library.bookstore.model.response.ApiResponse;
import co.library.bookstore.model.response.BookResponse;
import co.library.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${library.books.base.api}")
public class BookController {
    @Autowired
    private BookService service;

    private final ApiResponse apiResponse = new ApiResponse();

    @GetMapping
    public ResponseEntity<?> getBooks(
            @RequestParam (required = false) String title,
            @RequestParam (required = false) String author,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            HttpServletRequest request
    ) throws AlreadyExistException {
        List<BookResponse> books = this.service.book(title, author, page, size, sortBy, sortOrder);
        return apiResponse.succeed(books,200,"All Books Returned",request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable (value = "id") Long Id, HttpServletRequest request) throws NoSuchElementException{
        BookResponse book = this.service.book(Id);
        return apiResponse.succeed(book,200,"Book returned",request);
    }

    @GetMapping("/publishers/{id}")
    public ResponseEntity<?> getBooksOfPublisher(
            @PathVariable (name ="id") Long id,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            HttpServletRequest request
    ) throws AlreadyExistException {
        List<BookResponse> books = this.service.bookByPublisher(id, page, size, sortBy, sortOrder);
        return apiResponse.succeed(books,200,"All Books that this publisher published are Returned",request);
    }

    @PostMapping(path = "", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<?> saveBook(@Valid @ModelAttribute Init body, HttpServletRequest request) throws RuntimeException {

        final Date firstPublication = body.getFirstPublication();
        final Date lastPublication = body.getLastPublication();

        if(firstPublication == null || lastPublication == null) {
            return apiResponse.failed(400,"Dates can't be null","NULL_DATE_ERROR",request);
        } else if (firstPublication.after(lastPublication)) {
            return apiResponse.failed(400,"FirstPublication Can't be After LastPublication","DATE_FORMAT",request);
        } else if(lastPublication.after(new Date())) {
            return apiResponse.failed(400,"LastPublication is in future!!","DATE_FORMAT",request);
        }
        BookResponse book = this.service.save(body);
        return apiResponse.succeed(book,201,"Book saved",request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable (value = "id") Long Id,
            @Valid @RequestBody UpdateReq body,
            HttpServletRequest request
            ) throws NoSuchElementException, InvalidDateException {
        BookResponse book = this.service.update_book(Id,body);
        return apiResponse.succeed(book,200,"Book updated",request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable (value = "id") Long Id,
            HttpServletRequest request
    ) throws NoSuchElementException {
        this.service.delete_book(Id);
        return apiResponse.succeed("DELETED", 200, "ALl OK", request);
    }


}
