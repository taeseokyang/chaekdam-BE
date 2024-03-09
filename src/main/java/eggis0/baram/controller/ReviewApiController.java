package eggis0.baram.controller;

import eggis0.baram.domain.Review;
import eggis0.baram.dto.request.AddReviewRequest;
import eggis0.baram.dto.response.ReviewResponse;
import eggis0.baram.dto.response.ReviewsResponse;
import eggis0.baram.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
