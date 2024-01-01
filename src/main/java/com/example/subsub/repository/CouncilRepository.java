package com.example.subsub.repository;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CouncilRepository extends JpaRepository<Council, Integer> {
    void deleteByCouncilId(Integer councilId);

    List<Council> findAllByOrderByCollege();
    Council findByManager(User manager);
}
