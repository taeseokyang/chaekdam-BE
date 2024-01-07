package com.example.subsub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
}