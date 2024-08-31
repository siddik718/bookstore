package co.library.bookstore.model.response;

import co.library.bookstore.model.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private int rating;
    private String comment;
    @JsonIgnore
    private Book book;
    private Long reveiwerId;
}
