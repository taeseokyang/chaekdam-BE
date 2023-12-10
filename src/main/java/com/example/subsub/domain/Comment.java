//package com.example.subsub.domain;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long commentId;
//
//    @Column(nullable = false)
//    private String text;
//
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(nullable = false)
//    private boolean isSecret;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "postId")
//    @JsonIgnore
//    private Post post;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id")
//    @JsonIgnore
//    private User user;
//}
