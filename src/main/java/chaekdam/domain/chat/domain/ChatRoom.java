package chaekdam.domain.chat.domain;

import chaekdam.domain.book.domain.Book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String roomId;
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;
    private Long peopleCount;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}