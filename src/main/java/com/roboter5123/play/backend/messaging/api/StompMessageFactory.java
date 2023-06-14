package com.roboter5123.play.backend.messaging.api;
import com.roboter5123.play.backend.messaging.model.StompMessage;

import java.util.Map;

public interface StompMessageFactory {

    StompMessage getMessage(Object payload) throws UnsupportedOperationException;

    StompMessage getMessage(Map<String, Object> payload) throws UnsupportedOperationException;

    StompMessage getAckMessage();
}