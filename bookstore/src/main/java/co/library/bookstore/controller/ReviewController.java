package co.library.bookstore.controller;

import co.library.bookstore.exception.AlreadyExistException;
import co.library.bookstore.model.request.NewReview;
import co.library.bookstore.model.response.ApiResponse;
import co.library.bookstore.model.response.ReviewResponse;
import co.library.bookstore.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${library.reviews.base.api}")
public class ReviewController {
    @Autowired
    private ReviewService service;

    private final ApiResponse apiResponse = new ApiResponse();

    @PostMapping
    public ResponseEntity<?> newReview(@Valid @RequestBody NewReview body,HttpServletRequest request) {
        final Double rating = body.getRating();
        if (rating < 0 || rating > 5) {
            return apiResponse.failed(400, "Rating should be 0-5!", "RATING_LIMIT_ERROR", request);
        }
        ReviewResponse res = this.service.save(body);
        return apiResponse.succeed(res,201,"Reviews Saved",request);
    }
    @GetMapping("/reviewer/{id}")
    public ResponseEntity<?> getReviewByReviewer(
            @PathVariable (name ="id") Long id,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            HttpServletRequest request
    ) throws AlreadyExistException {
        List<ReviewResponse> books = this.service.reviewByReviewer(id, page, size, sortBy, sortOrder);
        return apiResponse.succeed(books,200,"All Reviews that this reviewer gave are Returned",request);
    }
}
