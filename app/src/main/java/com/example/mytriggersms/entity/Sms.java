package com.example.mytriggersms.entity;

public class Sms {
    private int ID;
    private String password;
    private String smsTitle;
    private String status;

    public Sms() {
    }

    public Sms(String password, String smsTitle, String status) {
        this.password = password;
        this.smsTitle = smsTitle;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
