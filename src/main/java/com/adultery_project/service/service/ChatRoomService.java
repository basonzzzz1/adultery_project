package com.adultery_project.service.service;

import com.adultery_project.models.ChatRoom;

import java.util.List;
import java.util.UUID;

public interface ChatRoomService {
    public ChatRoom save(ChatRoom chatRoom);
    public ChatRoom edit(ChatRoom chatRoom);
    public void delete(UUID id);
    public ChatRoom findById(UUID id);
    public List<ChatRoom> getAll();
    boolean existsByPassword(String password);
}
