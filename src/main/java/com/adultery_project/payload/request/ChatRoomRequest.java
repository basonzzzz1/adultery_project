package com.adultery_project.payload.request;

public class ChatRoomRequest {
    private String username;
        private String password;
    private int point;

    public ChatRoomRequest() {
    }

    public ChatRoomRequest(String username, String password, int point) {
        this.username = username;
        this.password = password;
        this.point = point;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}