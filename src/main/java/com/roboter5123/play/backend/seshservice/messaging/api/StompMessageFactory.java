package com.roboter5123.play.backend.seshservice.messaging.api;
import com.roboter5123.play.backend.seshservice.messaging.model.message.StompMessage;

public interface StompMessageFactory {

    StompMessage getMessage(Object payload) throws UnsupportedOperationException;

    StompMessage getAckMessage();
}