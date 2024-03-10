package eggis0.baram.domain.review.application;

import eggis0.baram.domain.post.domain.Post;
import eggis0.baram.domain.post.repository.PostRepository;
import eggis0.baram.domain.review.domain.RateType;
import eggis0.baram.domain.review.domain.Review;
import eggis0.baram.domain.review.dto.req.AddReviewRequest;
import eggis0.baram.domain.review.dto.res.ReviewResponse;
import eggis0.baram.domain.review.dto.res.ReviewsResponse;
import eggis0.baram.domain.review.repository.ReviewRepository;
import eggis0.baram.domain.user.domain.User;
import eggis0.baram.domain.user.domain.UserType;
import eggis0.baram.domain.user.exception.UserNotFountException;
import eggis0.baram.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponse save(AddReviewRequest request) throws Exception {
        User recipient = userRepository.findById(request.getRecipientId()).get();

        if (request.getWriterType() == UserType.LENDER) {
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
        Review savedReview = reviewRepository.save(review);
        return new ReviewResponse(savedReview);
    }

    public ReviewsResponse get(String userId) throws Exception {
        if (!userRepository.existsUserById(userId)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(userId).get();
        List<Review> reviews = reviewRepository.findTop3ByRecipientOrderByCreatedAtDesc(user);
        ReviewsResponse reviewsResponse = new ReviewsResponse();
        reviewsResponse.setLoveCount(reviewRepository.countByRecipientAndRate(user, RateType.LOVE));
        reviewsResponse.setGoodCount(reviewRepository.countByRecipientAndRate(user, RateType.GOOD));
        reviewsResponse.setBadCount(reviewRepository.countByRecipientAndRate(user, RateType.BAD));
        reviewsResponse.setReviews(reviews);
        return reviewsResponse;
    }
}