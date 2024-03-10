package eggis0.baram.domain.review.repository;


import eggis0.baram.domain.review.domain.RateType;
import eggis0.baram.domain.review.domain.Review;
import eggis0.baram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findTop3ByRecipientOrderByCreatedAtDesc(User recipient);

    Integer countByRecipientAndRate(User recipient, RateType rate);
}