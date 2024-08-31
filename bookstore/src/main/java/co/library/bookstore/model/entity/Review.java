package co.library.bookstore.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_id")
    private Long reviewId;

    @Column(nullable=false)
    private Double rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    @JsonIgnore
    private Book book;

    private Long reviewerId;
}
