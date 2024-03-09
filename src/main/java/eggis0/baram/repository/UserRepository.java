package eggis0.baram.repository;

import eggis0.baram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findById(String id);
//    Long countUserByUserId(String userId);
    Boolean existsUserByUserId(String userId);
    Boolean existsUserById(String id);
}