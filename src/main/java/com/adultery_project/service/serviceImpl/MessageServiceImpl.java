package com.adultery_project.service.serviceImpl;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.models.Message;
import com.adultery_project.models.User;
import com.adultery_project.repository.MessageRepository;
import com.adultery_project.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message edit(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message findById(int id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
    @Override
    public List<Message> finAllByLoggerInUser() {
        Optional<User> principal = userDetailsService.getLoggedInUser();
        return messageRepository.findAllByUser(principal);
    }

    @Override
    public List<Message> findByChatRoom(ChatRoom chatRoom) {
        return messageRepository.findByChatRoom(chatRoom);
    }

    @Override
    public void deleteAllByChatRoom_Id(UUID chatRoomId) {
        messageRepository.deleteAllByChatRoom_Id(chatRoomId);
    }


}