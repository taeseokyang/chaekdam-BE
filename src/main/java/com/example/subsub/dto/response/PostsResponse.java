package com.example.subsub.dto.response;

import com.example.subsub.domain.Comment;
import com.example.subsub.domain.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor//이거 왜 해야함?
@Getter
@Setter
public class PostsResponse {

    private Integer postId;
    private String title;
    private String location;
    private String locationDetail;
    private Long rentalFee;
    private LocalDateTime createdAt;
    private Integer commentCount;
    private boolean isClose;
    private String userId;

//    @Builder
//    public PostsResponse(Post post, Integer commentCount){
//        postId = post.getPostId();
//        title = post.getTitle();
//        location = post.getLocation();
//        locationDetail = post.getLocationDetail();
//        rentalFee = post.getRentalFee();
//        createdAt = post.getCreatedAt();
//        commentCount = commentCount;
//        userId = post.getUser().getUserId();
//    }
}
