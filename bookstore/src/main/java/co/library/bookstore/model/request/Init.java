package co.library.bookstore.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Init {
    @NotBlank
    @Size(min = 3)
    private String title;
    @NotBlank
    @Size(min = 3)
    private String author;
    @NotBlank
    @Size(min = 10, max = 150)
    private String shortDescription;
    @NotNull
    private Date lastPublication;
    @NotNull
    private Date firstPublication;

    @NotNull
    private Long publisherId;

    @NotNull
    private MultipartFile bookCoverImage;
    @NotNull
    private MultipartFile book;

    public Init(String title, String author, String shortDescription, String lastPublication, String firstPublication, MultipartFile book, MultipartFile bookCoverImage) throws ParseException {
        this.title = title;
        this.author = author;
        this.shortDescription = shortDescription;
        this.lastPublication = new SimpleDateFormat("yyyy-MM-dd").parse(lastPublication);
        this.firstPublication = new SimpleDateFormat("yyyy-MM-dd").parse(firstPublication);;
        this.book = book;
        this.bookCoverImage = bookCoverImage;
    }

    public void setFirstPublication(String firstPublication) throws ParseException {
        if (firstPublication != null) {
            this.firstPublication = new SimpleDateFormat("yyyy-MM-dd").parse(firstPublication);
        }
    }

    public void setLastPublication(String lastPublication) throws ParseException{
        if (lastPublication != null) {
            this.lastPublication = new SimpleDateFormat("yyyy-MM-dd").parse(lastPublication);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", lastPublication=" + lastPublication +
                ", firstPublication=" + firstPublication +
                '}';
    }
}
