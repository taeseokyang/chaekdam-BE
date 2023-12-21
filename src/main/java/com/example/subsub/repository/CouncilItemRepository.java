package com.example.subsub.repository;

import com.example.subsub.domain.Council;
import com.example.subsub.domain.CouncilItem;
import com.example.subsub.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CouncilItemRepository extends JpaRepository<CouncilItem, Integer> {
    void deleteByItemId(Integer councilItemId);

    Integer countByCouncilAndType(Council council, ItemType type);
}
