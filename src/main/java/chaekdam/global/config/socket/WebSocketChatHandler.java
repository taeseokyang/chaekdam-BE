package chaekdam.global.config.socket;

import chaekdam.domain.participant.domain.Participant;
import chaekdam.domain.participant.exception.ParticipantNotFountException;
import chaekdam.domain.participant.repository.ParticipantRepository;
import chaekdam.domain.user.domain.User;
import chaekdam.domain.user.exception.UserNotFountException;
import chaekdam.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import chaekdam.domain.chat.application.ChatService;
import chaekdam.domain.chat.domain.ChatRoom;
import chaekdam.domain.chat.dto.ChatDTO;
import chaekdam.domain.chat.exception.FailSendMessageException;
import chaekdam.domain.chat.repository.ChatRoomRepository;
import chaekdam.domain.message.application.MessageService;
import chaekdam.domain.message.domain.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    private Map<WebSocketSession, Long> sessionToIdMap = new HashMap<>();
    private Map<Long, WebSocketSession> idToSessionMap = new HashMap<>();
    private Long session_Idx = 0L;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("{} 연결됨", session.getId());
        handleAction(session, chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long removedSessionIdx = sessionToIdMap.remove(session);
        idToSessionMap.remove(removedSessionIdx);
        log.info("{} 연결 끊김", session.getId());
        log.info("세션 메모리 크기 {}", sessionToIdMap.size());
    }

    public void handleAction(WebSocketSession session, ChatDTO message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        User user = userRepository.findByUserId(message.getUserId()).orElseThrow(UserNotFountException::new);
        Participant participant = participantRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(ParticipantNotFountException::new);
        if (message.getType().equals(MessageType.ENTER)) {
            session_Idx += 1;
            sessionToIdMap.put(session, session_Idx);
            idToSessionMap.put(session_Idx, session);
            participant.setSessionId(session_Idx);
            participantRepository.save(participant);
        } else if (message.getType().equals(MessageType.TALK)) {
            messageService.save(message.getMessage(), message.getRoomId(), message.getUserId());
            message.setMessage(message.getMessage());
            sendMessage(message, chatRoom);
        }
    }

    public <T> void sendMessage(T message, ChatRoom chatRoom) {
        try {
            List<Participant> participantList = participantRepository.findAllByChatRoom(chatRoom);
            for(Participant participant : participantList){
                chatService.sendMessage(idToSessionMap.get(participant.getSessionId()), message);
            }
        } catch (Exception e) {
            throw new FailSendMessageException();
        }
    }
}