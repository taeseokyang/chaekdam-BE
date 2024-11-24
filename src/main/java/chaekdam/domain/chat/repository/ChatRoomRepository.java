package chaekdam.domain.chat.repository;

import chaekdam.domain.book.domain.Book;
import chaekdam.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Boolean existsByRoomId(String roomId);

    ChatRoom findByRoomId(String roomId);

    Optional<ChatRoom> findByBook(Book book);
}
