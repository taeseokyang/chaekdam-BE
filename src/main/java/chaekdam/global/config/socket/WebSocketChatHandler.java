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
import org.springframework.data.redis.core.RedisTemplate;
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

    private final RedisTemplate<String, String> redisTemplate;
    private Map<WebSocketSession, String> sessionToIdMap = new HashMap<>();
    private Map<String, WebSocketSession> idToSessionMap = new HashMap<>();
    private Long session_Idx = 0L;

    // 메세지가 왔을 때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("{} 연결됨", session.getId());
        handleAction(session, chatMessage);
    }

    // 연결 종류 후
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String removedSessionIdx = sessionToIdMap.remove(session);
        idToSessionMap.remove(removedSessionIdx);
        log.info("{} 연결 끊김", session.getId());
        log.info("세션 메모리 크기 {}", sessionToIdMap.size());
    }

    public void handleAction(WebSocketSession session, ChatDTO message) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        User user = userRepository.findByUserId(message.getUserId()).orElseThrow(UserNotFountException::new);
        Participant participant = participantRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(ParticipantNotFountException::new);

        if (message.getType().equals(MessageType.ENTER)) { // 채팅방 입장 시
            // 세션 객체와 세션 id 양방향 Map 제작
            session_Idx += 1;
            sessionToIdMap.put(session, session_Idx.toString());
            idToSessionMap.put(session_Idx.toString(), session);
            // 레디스에 세션 id 기록
            redisTemplate.opsForValue().set(participant.getUser().getUserId(), session_Idx.toString());
        } else if (message.getType().equals(MessageType.TALK)) { // 메세지 전송 시
            // 메세지 기록
            messageService.save(message.getMessage(), message.getRoomId(), message.getUserId());
            // 메세지 전송
            message.setMessage(message.getMessage());
            sendMessage(message, chatRoom);
        }
    }

    // 메세지 전송
    public <T> void sendMessage(T message, ChatRoom chatRoom) {
        try {
            List<Participant> participantList = participantRepository.findAllByChatRoom(chatRoom);
            for(Participant participant : participantList){
                String session_Idx = redisTemplate.opsForValue().get(participant.getUser().getUserId()); // 세션 id 가져오기
                chatService.sendMessage(idToSessionMap.get(session_Idx), message);
            }
        } catch (Exception e) {
            throw new FailSendMessageException();
        }
    }
}