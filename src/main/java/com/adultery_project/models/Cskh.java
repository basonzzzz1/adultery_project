package com.adultery_project.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Cskh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    private String content;
    private LocalDateTime createAt;
    private boolean seen;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;

    public Cskh() {
    }

    public Cskh( String content, LocalDateTime createAt, boolean seen, User fromUser, User toUser) {
        this.content = content;
        this.createAt = createAt;
        this.seen = seen;
        this.fromUser = fromUser;
        this.toUser = toUser;
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

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}