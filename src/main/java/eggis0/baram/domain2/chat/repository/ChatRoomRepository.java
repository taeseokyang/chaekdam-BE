package eggis0.baram.domain2.chat.repository;

import eggis0.baram.domain2.chat.domain.ChatRoom;
import eggis0.baram.domain2.post.domain.Post;
import eggis0.baram.domain2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    Boolean existsByRoomId(String roomId);
    ChatRoom findByRoomId(String roomId);
    List<ChatRoom> findAllByBorrowerOrderByCreatedAtDesc(User borrower);
    List<ChatRoom> findAllByLenderOrderByCreatedAtDesc(User lender);

    Optional<ChatRoom> findByBorrowerAndLenderAndPost(User borrower, User Lender, Post post);
}
