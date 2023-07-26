package com.pixelthump.seshservice.messaging.api;
import com.pixelthump.seshservice.messaging.model.message.StompMessage;

public interface StompMessageFactory {

    StompMessage getMessage(Object payload) throws UnsupportedOperationException;

    StompMessage getAckMessage();
}