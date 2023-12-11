package com.example.subsub.repository;

import com.example.subsub.domain.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CouncilRepository extends JpaRepository<Council, Integer> {
    void deleteByCouncilId(Integer councilId);
}
