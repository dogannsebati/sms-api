package com.example.mytriggersms.entity;

import java.time.ZonedDateTime;

public class ComingSms {

    private int ID;
    private String sender;
    private String content;

    public ComingSms(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public ComingSms() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
