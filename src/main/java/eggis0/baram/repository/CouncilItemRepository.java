package eggis0.baram.repository;

import eggis0.baram.domain.Council;
import eggis0.baram.domain.CouncilItem;
import eggis0.baram.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CouncilItemRepository extends JpaRepository<CouncilItem, Integer> {
    void deleteByItemId(Integer councilItemId);

    Integer countByCouncilAndType(Council council, ItemType type);
}
