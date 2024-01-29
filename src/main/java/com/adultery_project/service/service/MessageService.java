package com.adultery_project.service.service;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.models.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService extends IService<Message>{
      List<Message> finAllByLoggerInUser();
      List<Message> findByChatRoom(ChatRoom chatRoom);
      void deleteAllByChatRoom_Id(UUID chatRoomId);
}
