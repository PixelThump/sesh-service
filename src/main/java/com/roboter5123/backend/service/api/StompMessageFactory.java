package com.roboter5123.backend.service.api;
import com.roboter5123.backend.service.model.StompMessage;

import java.util.Map;

public interface StompMessageFactory {

    StompMessage getMessage(Object payload) throws UnsupportedOperationException;
    StompMessage getMessage(Map<String, Object> payload) throws UnsupportedOperationException;
}
