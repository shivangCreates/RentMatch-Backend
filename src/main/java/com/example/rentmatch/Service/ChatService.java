package com.example.rentmatch.Service;

import com.example.rentmatch.Entity.ChatMessage;
import com.example.rentmatch.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatRepository;

    public ChatMessage save(ChatMessage message) {

        message.setTimestamp(LocalDateTime.now());

        return chatRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(
            Long propertyId,
            Long senderId,
            Long receiverId
    ) {

        return chatRepository.getChatHistory(
                propertyId,
                senderId,
                receiverId
        );
    }

}
