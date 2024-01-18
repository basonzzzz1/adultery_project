package com.adultery_project.payload.request;

public class CskhRepuest {
     private String content;

    public CskhRepuest() {
    }

    public CskhRepuest( String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}