package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Message;
import com.rishika.campuscart.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

    private final MessageRepository messageRepository;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }
    @GetMapping("/{userId}")
    public List<Message> getMessages(@PathVariable Long userId){
        return messageRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    @GetMapping("/{user1}/{user2}")
    public List<Message> getChat(
            @PathVariable Long user1,
            @PathVariable Long user2){

        return messageRepository.findChat(user1, user2);
    }

}
