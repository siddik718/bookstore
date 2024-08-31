package co.library.bookstore.service.Impl;

import co.library.bookstore.exception.NoResourceFoundException;
import co.library.bookstore.model.entity.Book;
import co.library.bookstore.model.entity.Review;
import co.library.bookstore.model.request.NewReview;
import co.library.bookstore.model.response.ReviewResponse;
import co.library.bookstore.repository.BookRepository;
import co.library.bookstore.repository.ReviewRepository;
import co.library.bookstore.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewImplService implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ReviewResponse save(NewReview body) throws NoResourceFoundException{
        Optional<Book> book = this.bookRepository.findById(body.getBookId());
        if (book.isEmpty()) {
            throw new NoResourceFoundException("No Book Exist!");
        }
        Book bok = this.mapper.map(book, Book.class);

        Review newReview = new Review();
        newReview.setRating(body.getRating());
        newReview.setComment(body.getComment());
        newReview.setReviewerId(body.getReviewerId());
        newReview.setBook(bok);

        Review review = this.reviewRepository.save(newReview);
        return this.mapper.map(review, ReviewResponse.class);
    }

    @Override
    public List<ReviewResponse> reviewByReviewer(Long reviewerId, int page, int size, String sortBy, String sortOrder)
            throws RuntimeException {
        Sort sort = Sort.by(sortBy != null ? sortBy : "id");
        sort = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page,size);
        Page<Review> reviews;
        reviews = reviewRepository.findByReviewerId(reviewerId, pageable);
        System.out.println("Reviews: " + reviews);
        return reviews.stream()
                .map(review -> mapper.map(review, ReviewResponse.class))
                .collect(Collectors.toList());
    }
}
