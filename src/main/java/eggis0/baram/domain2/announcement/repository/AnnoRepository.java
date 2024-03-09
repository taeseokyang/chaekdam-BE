package eggis0.baram.domain2.announcement.repository;

import eggis0.baram.domain2.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AnnoRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findAllByOrderByCreatedAtDesc();
}
