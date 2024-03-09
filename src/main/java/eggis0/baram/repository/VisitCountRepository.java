package eggis0.baram.repository;

import eggis0.baram.domain.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface VisitCountRepository extends JpaRepository<VisitCount, Integer> {
    boolean existsByDay(LocalDate day);
    VisitCount findByDay(LocalDate day);
}
