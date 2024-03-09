package eggis0.baram.repository;

import eggis0.baram.domain.Council;
import eggis0.baram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CouncilRepository extends JpaRepository<Council, Integer> {
    void deleteByCouncilId(Integer councilId);
    Long countAllBy();
    List<Council> findAllByOrderByCollege();
    List<Council> findAllByCollegeStartingWithOrderByCollege(String prefix);
    Council findByManager(User manager);
}
