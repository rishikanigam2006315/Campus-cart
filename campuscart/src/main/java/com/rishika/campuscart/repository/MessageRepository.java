package com.rishika.campuscart.repository;

import com.rishika.campuscart.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    //List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

}