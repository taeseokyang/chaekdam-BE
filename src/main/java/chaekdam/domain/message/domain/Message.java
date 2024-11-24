package chaekdam.domain.message.domain;

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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
}
