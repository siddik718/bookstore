package co.library.bookstore.model.response;

import co.library.bookstore.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long bookId;
    private String title;
    private String author;
    private String shortDescription;
    private Date firstPublication;
    private Date lastPublication;
    private List<Review> reviews;
    private double averageRating;
    private String booksRemoteUrl;
    private String booksCoverImageUrl;
    private Long publisherId;
}
