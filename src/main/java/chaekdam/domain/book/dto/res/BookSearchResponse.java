package chaekdam.domain.book.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class BookSearchResponse {
    private String isbn;
    private String title;
    private String bookCoverImgName;
    private String author;
    private String publisher;

    @Builder
    public BookSearchResponse(String isbn, String title, String bookCoverImgName, String author, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.bookCoverImgName = bookCoverImgName;
        this.author = author;
        this.publisher = publisher;
    }
}
