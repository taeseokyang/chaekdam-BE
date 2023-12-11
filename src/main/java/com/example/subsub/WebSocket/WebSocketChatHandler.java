package com.example.subsub.WebSocket;

import com.example.subsub.domain.*;
import com.example.subsub.dto.chat.ChatDTO;
import com.example.subsub.repository.ChatRoomRepository;
import com.example.subsub.service.ChatService;
import com.example.subsub.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
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

    private Map<WebSocketSession, Long> sessionToIdMap = new HashMap<>();
    private Map<Long, WebSocketSession> idToSessionMap = new HashMap<>();
    private Long session_Idx = 0L;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("session {}", chatMessage.toString());

        handleAction(session, chatMessage, chatMessage.getRoomId(),chatService);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        Long removedSessionIdx = sessionToIdMap.remove(session);
        idToSessionMap.remove(removedSessionIdx);
    }

    public void handleAction(WebSocketSession session, ChatDTO message, String roodId,ChatService chatService) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roodId);

        if (message.getType().equals(MessageType.ENTER)) {
            session_Idx += 1;

            sessionToIdMap.put(session,session_Idx);
            idToSessionMap.put(session_Idx,session);

            if (message.getUserType().equals(UserType.BORROWER))
                chatRoom.setBorrowerSessionId(session_Idx);
            else if(message.getUserType().equals(UserType.LENDER))
                chatRoom.setLenderSessionId(session_Idx);

            chatRoomRepository.save(chatRoom);
            message.setMessage(message.getSender() + " 님이 입장하셨습니다");
            sendMessage(message, chatRoom,chatService);
        } else if (message.getType().equals(MessageType.TALK)) {
            messageService.save(message.getMessage(),roodId,message.getSender(),message.getUserType());
            message.setMessage(message.getMessage());
            sendMessage(message, chatRoom, chatService);
        }
    }

    public <T> void sendMessage(T message, ChatRoom chatRoom,ChatService chatService) {
        try{
            chatService.sendMessage(idToSessionMap.get(chatRoom.getBorrowerSessionId()),message);
        }catch (Exception e){

        }
        try{
            chatService.sendMessage(idToSessionMap.get(chatRoom.getLenderSessionId()),message);
        }catch (Exception e){

        }
    }
}