package chaekdam.domain.participant.repository;

import chaekdam.domain.book.domain.Book;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.participant.domain.Participant;
import chaekdam.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByChatRoomAndUser(ChatRoom chatRoom, User user);
    List<Participant> findAllByChatRoom(ChatRoom chatRoom);
    List<Participant> findAllByUser(User user);
}
