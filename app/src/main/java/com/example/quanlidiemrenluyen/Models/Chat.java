package com.example.quanlidiemrenluyen.Models;

public class Chat {
    private String sender;
    private String receiver;
    private String mesenger;

    public Chat(String sender, String receiver, String mesenger) {
        this.sender = sender;
        this.receiver = receiver;
        this.mesenger = mesenger;
    }
    public Chat(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMesenger() {
        return mesenger;
    }

    public void setMesenger(String mesenger) {
        this.mesenger = mesenger;
    }
}
