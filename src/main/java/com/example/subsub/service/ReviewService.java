package com.example.subsub.service;

import com.example.subsub.domain.*;
import com.example.subsub.dto.request.AddCouncilRequest;
import com.example.subsub.dto.request.AddReviewRequest;
import com.example.subsub.dto.request.UpdateCouncilRequest;
import com.example.subsub.dto.request.UserRequest;
import com.example.subsub.dto.response.*;
import com.example.subsub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;

    // 생성
    public Review save(AddReviewRequest request) throws Exception {
        User recipient = userRepository.findById(request.getRecipientId()).get();

        if (request.getWriterType() == UserType.LENDER){
            Post post = postRepository.findByPostId(request.getPostId()).get();
            post.setIsLenderWriteReview(true);
            postRepository.save(post);
        }

        LocalDateTime createdAt = LocalDateTime.now();
        Review review = Review.builder()
                .writerType(request.getWriterType())
                .rate(request.getRate())
                .text(request.getText())
                .createdAt(createdAt)
                .recipient(recipient)
                .build();
        return reviewRepository.save(review);
    }

    public ReviewsResponse get(String userId) throws Exception {
        System.out.print(userId);
        User user;
        if (userRepository.existsUserById(userId)){
            user = userRepository.findById(userId).get();
        }else{
            return new ReviewsResponse();
        }
        List<Review> reviews = reviewRepository.findTop3ByRecipientOrderByCreatedAtDesc(user);

        ReviewsResponse reviewsResponse = new ReviewsResponse();
        reviewsResponse.setLoveCount(reviewRepository.countByRecipientAndRate(user, RateType.LOVE));
        reviewsResponse.setGoodCount(reviewRepository.countByRecipientAndRate(user, RateType.GOOD));
        reviewsResponse.setBadCount(reviewRepository.countByRecipientAndRate(user, RateType.BAD));
        reviewsResponse.setReviews(reviews);
        return reviewsResponse;
    }
}