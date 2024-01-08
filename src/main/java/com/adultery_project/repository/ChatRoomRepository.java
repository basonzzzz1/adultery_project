package com.adultery_project.repository;

import com.adultery_project.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom , UUID> {
         boolean existsByPassword(String password);
}
