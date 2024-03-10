package eggis0.baram.domain.review.presentation;

import eggis0.baram.domain.review.application.ReviewService;
import eggis0.baram.domain.review.dto.req.AddReviewRequest;
import eggis0.baram.domain.review.dto.res.ReviewResponse;
import eggis0.baram.domain.review.dto.res.ReviewsResponse;
import eggis0.baram.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static eggis0.baram.domain.review.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static eggis0.baram.domain.review.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseDto<ReviewResponse> save(@RequestBody AddReviewRequest request) throws Exception {
        ReviewResponse response = reviewService.save(request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{userId}")
    public ResponseDto<ReviewsResponse> get(@PathVariable String userId) throws Exception {
        ReviewsResponse response = reviewService.get(userId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}
