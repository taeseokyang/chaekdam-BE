package com.example.subsub.repository;

import com.example.subsub.domain.Certification;
import com.example.subsub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByOrderByUserIsCertification();
}