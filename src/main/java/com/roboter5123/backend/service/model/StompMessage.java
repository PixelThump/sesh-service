package com.roboter5123.backend.service.model;
public abstract class StompMessage {

    private StompMessageType messageType;

    public StompMessageType getMessageType() {

        return messageType;
    }

    public void setMessageType(StompMessageType messageType) {

        this.messageType = messageType;
    }
}
