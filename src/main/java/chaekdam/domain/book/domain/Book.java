package chaekdam.domain.book.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Book {
    @Id
    private String isbn;
    @Column(nullable = false)
    private String title;
    private String author;
    private String publisher;
    private String coverImgName;
}