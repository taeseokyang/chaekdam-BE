package chaekdam.domain.book.dto.res;

import lombok.*;
import chaekdam.domain.book.domain.Book;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse {
    private String bookId;

    public BookResponse(Book book) {
        bookId = book.getIsbn();
    }
}
