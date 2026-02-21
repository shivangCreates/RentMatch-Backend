package com.example.rentmatch.Controller;

import com.example.rentmatch.Entity.ChatMessage;
import com.example.rentmatch.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository chatRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {

        message.setTimestamp(LocalDateTime.now());

        chatRepository.save(message);

        messagingTemplate.convertAndSend(
                "/topic/messages/" + message.getReceiverId(),
                message
        );
    }

}
