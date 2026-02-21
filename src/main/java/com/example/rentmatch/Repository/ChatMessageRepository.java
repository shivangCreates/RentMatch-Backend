package com.example.rentmatch.Repository;

import com.example.rentmatch.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<ChatMessage> getChatHistory(
            Long propertyId,
            Long user1,
            Long user2
    );
}
