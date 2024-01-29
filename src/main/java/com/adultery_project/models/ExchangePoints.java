package com.adultery_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExchangePoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private int point;
    private String bankCode;
    private String bankName;
    private String content;
    public ExchangePoints() {
    }

    public ExchangePoints(String username, int point, String bankCode, String bankName,String content) {
        this.username = username;
        this.point = point;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.content = content;
    }

    public ExchangePoints(int id, String username, int point, String bankCode, String bankName,String content) {
        this.id = id;
        this.username = username;
        this.point = point;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}