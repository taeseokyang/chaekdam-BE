package com.example.subsub.dto.response;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CommentResponse {


    private User userId;
    private Post postId;
    private String text;
    private LocalDate createdAt;
    private Boolean isSecret;

    public CommentResponse(Comment comment){
        userId = comment.getUser();
        postId = comment.getPost();
        text = comment.getText();
        createdAt = comment.getCreatedAt();
        isSecret = comment.isSecret();

    }
}
