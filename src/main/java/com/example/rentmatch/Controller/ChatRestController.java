package com.example.rentmatch.Controller;

import com.example.rentmatch.Entity.ChatMessage;
import com.example.rentmatch.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(

            @RequestParam Long propertyId,
            @RequestParam Long senderId,
            @RequestParam Long receiverId

    ) {

        return chatService.getChatHistory(
                propertyId,
                senderId,
                receiverId
        );

    }

}