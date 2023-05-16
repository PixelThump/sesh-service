package com.roboter5123.backend.stomp.model;
public class StompMessage {

    StompMessageType messageType;
    Object messageBody;

    public StompMessageType getMessageType() {

        return messageType;
    }

    public void setMessageType(StompMessageType messageType) {

        this.messageType = messageType;
    }

    public Object getMessageBody() {

        return messageBody;
    }

    public void setMessageBody(Object messageBody) {

        this.messageBody = messageBody;
    }
}
