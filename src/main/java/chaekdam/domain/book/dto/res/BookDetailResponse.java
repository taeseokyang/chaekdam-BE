package chaekdam.domain.book.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import chaekdam.domain.book.domain.Book;

@NoArgsConstructor
@Getter
@Setter
public class BookDetailResponse {
    private String isbn;
    private String title;
    private String bookCoverImgName;
    private String author;
    private String publisher;

    public BookDetailResponse(String isbn, String title, String bookCoverImgName, String author, String publisher, Long pageCount) {
        this.title = title;
        this.isbn = isbn;
        this.bookCoverImgName = bookCoverImgName;
        this.author = author;
        this.publisher = publisher;
    }

    public BookDetailResponse(Book book){
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.bookCoverImgName = book.getCoverImgName();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
    }
}
