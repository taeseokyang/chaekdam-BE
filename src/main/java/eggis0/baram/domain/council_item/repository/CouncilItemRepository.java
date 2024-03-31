package eggis0.baram.domain.council_item.repository;

import eggis0.baram.domain.council.domain.Council;
import eggis0.baram.domain.council_item.domain.CouncilItem;
import eggis0.baram.domain.council_item.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CouncilItemRepository extends JpaRepository<CouncilItem, Integer> {
    void deleteByItemId(Integer councilItemId);

    Integer countByCouncilAndType(Council council, ItemType type);

    List<CouncilItem> findTop5ByNameContaining(String keyword);
}
