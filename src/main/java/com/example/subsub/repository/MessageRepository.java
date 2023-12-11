package com.example.subsub.repository;

import com.example.subsub.domain.ChatRoom;
import com.example.subsub.domain.Message;
import com.example.subsub.domain.MessageType;
import com.example.subsub.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Post> findAllByLocationOrderByIsCloseAscCreatedAtDesc(String location);
    List<Message> findAllByChatRoom(ChatRoom chatRoom);

    Message findFirstByChatRoomOrderBySentAtDesc(ChatRoom chatRoom);
}
