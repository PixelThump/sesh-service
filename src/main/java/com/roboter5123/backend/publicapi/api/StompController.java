package com.roboter5123.backend.publicapi.api;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.service.exception.NoSuchSessionException;

public interface StompController {

    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
