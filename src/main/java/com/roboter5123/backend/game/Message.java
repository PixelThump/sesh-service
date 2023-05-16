package com.roboter5123.backend.game;
import com.roboter5123.backend.service.MessageType;

public interface Message<T> {

    MessageType getType();
    void setType(MessageType newType);
    void setMessageBody(T body);
    T getMessageBody();
}
