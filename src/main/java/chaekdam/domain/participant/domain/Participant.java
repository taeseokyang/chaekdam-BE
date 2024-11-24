package chaekdam.domain.participant.domain;

import chaekdam.domain.book.domain.Book;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.user.domain.User;
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
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Column
    private Long sessionId;
}