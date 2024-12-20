package chaekdam.domain.message.repository;

import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findTop20ByChatRoomAndChatIdLessThanOrderByChatIdDesc(ChatRoom chatRoom, Long lastMessageId);
    Optional<Message> findFirstByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);
}
