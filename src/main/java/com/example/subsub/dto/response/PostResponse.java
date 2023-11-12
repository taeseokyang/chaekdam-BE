package com.example.subsub.dto.response;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor//이거 왜 해야함?
@Getter
public class PostResponse {

    private Integer postId;
    private String title;
    private String location;
    private String locationDetail;
    private Long rentalFee;
    private LocalDateTime createdAt;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
    private Boolean isClose;
    private List<Comment> comments = new ArrayList<>();
    private String userId;

    @Builder
    public PostResponse(Post post){
        postId = post.getPostId();
        title = post.getTitle();
        location = post.getLocation();
        locationDetail = post.getLocationDetail();
        rentalFee = post.getRentalFee();
        createdAt = post.getCreatedAt();
        needAt = post.getNeedAt();
        returnAt = post.getReturnAt();
        content = post.getContent();
        isClose = post.getIsClose();
        comments = post.getComments();
        userId = post.getUser().getUserId();
    }
}
