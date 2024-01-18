package com.adultery_project.service.service;

import com.adultery_project.models.Cskh;
import com.adultery_project.models.User;

import java.util.List;

public interface CskhService extends IService<Cskh>{

    List<Cskh> getAllCskhInUser(Long firstUserId,Long secondUserId);
    List<Cskh> initialStateAllChatFriends(Long loggedInUserId);
    List<Cskh> findByFromUserOrToUser(User fromUser ,User toUser);
}
