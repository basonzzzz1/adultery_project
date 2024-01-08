package com.adultery_project.service.serviceImpl;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.repository.ChatRoomRepository;
import com.adultery_project.service.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom edit(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void delete(UUID id) {
        chatRoomRepository.deleteById(id);
    }

    @Override
    public ChatRoom findById(UUID id) {
        return chatRoomRepository.findById(id).get();
    }

    @Override
    public List<ChatRoom> getAll() {
        return chatRoomRepository.findAll();
    }

    @Override
    public boolean existsByPassword(String password) {
        return chatRoomRepository.existsByPassword(password);
    }
}