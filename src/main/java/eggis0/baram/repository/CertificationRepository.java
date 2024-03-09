package eggis0.baram.repository;

import eggis0.baram.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByOrderByUserIsCertification();
}