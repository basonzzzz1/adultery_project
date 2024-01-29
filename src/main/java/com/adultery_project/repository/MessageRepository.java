package com.adultery_project.repository;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.models.Message;
import com.adultery_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message , Integer> {
    List<Message> findAllByUser(Optional<User> user);
    List<Message> findByChatRoom(ChatRoom chatRoom);
    void deleteAllByChatRoom_Id(UUID chatRoomId);
}
