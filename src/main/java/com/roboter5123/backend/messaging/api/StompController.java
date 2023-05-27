package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;

public interface StompController {


    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
