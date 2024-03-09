package eggis0.baram.domain2.message.repository;

import eggis0.baram.domain2.chat.domain.ChatRoom;
import eggis0.baram.domain2.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatRoomOrderBySentAtAsc(ChatRoom chatRoom);

    Optional<Message> findFirstByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);
}
