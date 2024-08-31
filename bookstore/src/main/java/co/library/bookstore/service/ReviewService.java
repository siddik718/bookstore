package co.library.bookstore.service;

import java.util.List;

import co.library.bookstore.exception.NoResourceFoundException;
import co.library.bookstore.model.request.NewReview;
import co.library.bookstore.model.response.ReviewResponse;

public interface ReviewService {
    public ReviewResponse save(NewReview body) throws NoResourceFoundException;

    List<ReviewResponse> reviewByReviewer(Long reviewerId, int page, int size, String sortBy, String sortOrder) throws RuntimeException;
}
