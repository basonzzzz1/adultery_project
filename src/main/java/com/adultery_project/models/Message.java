package com.adultery_project.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private String content;
    @Lob
    private String image;
    private LocalDateTime createAt;
    private boolean seen;
    @ManyToOne
    private User user;
    @ManyToOne
    ChatRoom chatRoom;

    public Message() {
    }

    public Message(String content,String image, LocalDateTime createAt, boolean seen, User user, ChatRoom chatRoom) {
        this.content = content;
        this.image = image;
        this.createAt = createAt;
        this.seen = seen;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public Message(Integer id, String content, LocalDateTime createAt, boolean seen, User user, ChatRoom chatRoom) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.seen = seen;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}