package chaekdam.domain.participant.application;

import chaekdam.domain.book.application.BookService;
import chaekdam.domain.book.domain.Book;
import chaekdam.domain.book.exception.BookNotFoundException;
import chaekdam.domain.book.repository.BookRepository;
import chaekdam.domain.chat.application.ChatService;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.chat.repository.ChatRoomRepository;
import chaekdam.domain.participant.domain.Participant;
import chaekdam.domain.participant.exception.AlreadyParticipatingException;
import chaekdam.domain.participant.repository.ParticipantRepository;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Data
@Service
public class ParticipantService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final ParticipantRepository participantRepository;

    public void participate(String isbn, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(BookNotFoundException::new);
        Book book = bookService.getBook(isbn);
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByBook(book);
        ChatRoom chatRoom = optionalChatRoom.orElseGet(() -> chatService.save(isbn));

        Optional<Participant> alreadyExistParticipant = participantRepository.findByChatRoomAndUser(chatRoom, user);
        if (alreadyExistParticipant.isPresent())throw new AlreadyParticipatingException();
        chatService.increasePeopleCount(chatRoom);
        Participant participant = Participant.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
        participantRepository.save(participant);
    }
}
