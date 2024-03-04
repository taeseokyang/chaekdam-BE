package com.example.subsub.controller;

import com.example.subsub.domain.CouncilItem;
import com.example.subsub.domain.Review;
import com.example.subsub.dto.request.AddCouncilItemRequest;
import com.example.subsub.dto.request.AddReviewRequest;
import com.example.subsub.dto.request.UpdateCouncilItemRequest;
import com.example.subsub.dto.response.CouncilItemResponse;
import com.example.subsub.dto.response.RateResponse;
import com.example.subsub.dto.response.ReviewResponse;
import com.example.subsub.dto.response.ReviewsResponse;
import com.example.subsub.service.CouncilItemService;
import com.example.subsub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    // 생성
    @PostMapping
    public ReviewResponse save(@RequestBody AddReviewRequest request) throws Exception {
        Review review = reviewService.save(request);
        return new ReviewResponse(review);
    }

    // 모두 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ReviewsResponse> get(@PathVariable String userId) throws Exception {
        ReviewsResponse reviewsResponse = reviewService.get(userId);
        return ResponseEntity.ok(reviewsResponse);
    }
}
