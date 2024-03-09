package com.example.subsub.repository;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.User;
import com.example.subsub.domain.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface VisitCountRepository extends JpaRepository<VisitCount, Integer> {
    boolean existsByDay(LocalDate day);
    VisitCount findByDay(LocalDate day);
}
