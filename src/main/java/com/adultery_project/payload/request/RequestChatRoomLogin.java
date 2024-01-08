package com.adultery_project.payload.request;

import java.util.UUID;

public class RequestChatRoomLogin {
    private UUID id;
    private String password;

    public RequestChatRoomLogin(UUID id, String password) {
        this.id = id;
        this.password = password;
    }

    public RequestChatRoomLogin() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}