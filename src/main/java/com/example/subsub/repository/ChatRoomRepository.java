package com.example.subsub.repository;

import com.example.subsub.domain.ChatRoom;
import com.example.subsub.domain.Post;
import com.example.subsub.domain.User;
import com.example.subsub.service.ChatService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByRoomId(String roomId);
}
