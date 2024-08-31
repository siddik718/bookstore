package co.library.bookstore.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReq {
    @Size(min = 3)
    private String title;
    @Size(min = 3)
    private String author;
    @Size(min = 10, max = 150)
    private String shortDescription;
    private Date lastPublication;
    private Date firstPublication;

    @Override
    public String toString() {
        return "UpdateReq{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", lastPublication=" + lastPublication +
                ", firstPublication=" + firstPublication +
                '}';
    }
}
