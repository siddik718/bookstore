package co.library.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_id")
    private Long bookId;

    private String title;
    private String author;
    private String shortDescription;
    private Date firstPublication;
    private Date lastPublication;

    private String booksRemoteUrl;
    private String booksCoverImageUrl;

    private Long publisherId;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @Transient
    private double averageRating;

    @PostLoad
    private void calculateAverageRating() {
        if (reviews != null && !reviews.isEmpty()) {
            double sum = reviews.stream().mapToDouble(Review::getRating).sum();
            this.averageRating = sum / reviews.size();
        } else {
            this.averageRating = 0;
        }
    }


}
