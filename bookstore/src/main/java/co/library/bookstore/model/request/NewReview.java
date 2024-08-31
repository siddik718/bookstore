package co.library.bookstore.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReview {
    @NotNull
    private Double rating;
    @NotBlank
    @Size(min = 3)
    private String comment;
    @NotNull
    private Long bookId;
    @NotNull
    private Long reviewerId;
}
