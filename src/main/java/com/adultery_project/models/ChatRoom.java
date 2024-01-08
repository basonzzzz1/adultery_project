package com.adultery_project.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime createAt;
    private String password;
    @ManyToOne(optional = true)
    private User user1;

    @ManyToOne(optional = true)
    private User user2;

    public ChatRoom() {
    }

    public ChatRoom(LocalDateTime createAt, String password) {
        this.createAt = createAt;
        this.password = password;
    }

    public ChatRoom(LocalDateTime createAt, String password, User user1, User user2) {
        this.createAt = createAt;
        this.password = password;
        this.user1 = user1;
        this.user2 = user2;
    }

    public ChatRoom(UUID id, LocalDateTime createAt, String password, User user1, User user2) {
        this.id = id;
        this.createAt = createAt;
        this.password = password;
        this.user1 = user1;
        this.user2 = user2;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}