package eggis0.baram.domain2.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eggis0.baram.domain2.post.domain.Post;
import eggis0.baram.domain2.user.domain.User;
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
    private Integer Id;

    @Column(unique = true, nullable = false)
    private String roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User borrower;

    @Column(nullable = true)
    private Long borrowerSessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User lender;

    @Column(nullable = true)
    private Long lenderSessionId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}