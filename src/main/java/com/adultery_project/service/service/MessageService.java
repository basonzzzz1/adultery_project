package com.adultery_project.service.service;

import com.adultery_project.models.ChatRoom;
import com.adultery_project.models.Message;

import java.util.List;

public interface MessageService extends IService<Message>{
      public List<Message> finAllByLoggerInUser();
      List<Message> findByChatRoom(ChatRoom chatRoom);
}
