package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;

public interface MessageBroadcaster {

    void broadcast(String destination, StompMessage message);
}
