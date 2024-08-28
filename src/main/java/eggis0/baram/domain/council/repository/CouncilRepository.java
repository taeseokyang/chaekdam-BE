package eggis0.baram.domain.council.repository;

import eggis0.baram.domain.council.domain.Council;
import eggis0.baram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CouncilRepository extends JpaRepository<Council, Integer> {
    void deleteByCouncilId(Integer councilId);

    List<Council> findAllByIsVisibleOrderByCollege(boolean isVisible);


    List<Council> findAllByCollegeStartingWithAndIsVisibleOrderByCollege(String prefix, boolean isVisible);

    Council findByManager(User manager);

    boolean existsByManager(User manager);

    List<Council> findAllByNameContaining(String keyword);
}
