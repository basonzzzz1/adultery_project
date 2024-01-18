package com.adultery_project.service.serviceImpl;

import com.adultery_project.models.Cskh;
import com.adultery_project.models.User;
import com.adultery_project.repository.CskhRepository;
import com.adultery_project.service.service.CskhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CskhServiceImpl implements CskhService {
    @Autowired
    CskhRepository cskhRepository;
    @Override
    public Cskh save(Cskh cskh) {
        return cskhRepository.save(cskh);
    }

    @Override
    public Cskh edit(Cskh cskh) {
        return cskhRepository.save(cskh);
    }

    @Override
    public void delete(int id) {
       cskhRepository.deleteById(id);
    }

    @Override
    public Cskh findById(int id) {
        return cskhRepository.findById(id).get();
    }

    @Override
    public List<Cskh> getAll() {
        return cskhRepository.findAll();
    }

    @Override
    public List<Cskh> getAllCskhInUser(Long firstUserId, Long secondUserId) {
        return cskhRepository.findAllMessagesBetweenTwoUsers(firstUserId,secondUserId);
    }

    @Override
    public List<Cskh> initialStateAllChatFriends(Long loggedInUserId) {
        return cskhRepository.initialStateAllChatFriends(loggedInUserId);
    }

    @Override
    public List<Cskh> findByFromUserOrToUser(User fromUser, User toUser) {
        return cskhRepository.findByFromUserOrToUser(fromUser,toUser);
    }
}