package eggis0.baram.domain.review.presentation;

import eggis0.baram.domain.review.dto.res.ReviewResponse;
import eggis0.baram.domain.review.application.ReviewService;
import eggis0.baram.domain.review.dto.res.ReviewsResponse;
import eggis0.baram.domain.review.domain.Review;
import eggis0.baram.domain.review.dto.req.AddReviewRequest;
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
