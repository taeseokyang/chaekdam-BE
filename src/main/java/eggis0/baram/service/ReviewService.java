package eggis0.baram.service;

import eggis0.baram.domain.*;
import eggis0.baram.dto.request.AddReviewRequest;
import eggis0.baram.dto.response.ReviewsResponse;
import eggis0.baram.repository.PostRepository;
import eggis0.baram.repository.ReviewRepository;
import eggis0.baram.repository.UserRepository;
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