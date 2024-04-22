package eggis0.baram.domain.gachon_herald.repository;

import eggis0.baram.domain.gachon_herald.domain.GachonHerald;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GachonHeraldRepository extends JpaRepository<GachonHerald, Long> {
}