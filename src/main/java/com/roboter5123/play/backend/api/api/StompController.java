package com.roboter5123.play.backend.api.api;
import com.roboter5123.play.backend.messaging.model.StompMessage;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;

public interface StompController {

    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
