package com.example.subsub.repository;

import com.example.subsub.domain.CouncilItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CouncilItemRepository extends JpaRepository<CouncilItem, Integer> {
    void deleteByItemId(Integer councilItemId);
}
