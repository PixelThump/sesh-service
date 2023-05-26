package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.messaging.model.StompMessage;

public interface StompMessageFactory {

    StompMessage getMessage(Object payload) throws UnsupportedOperationException;
}
