package com.roboter5123.play.backend.api.api;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.messaging.model.StompMessage;

public interface StompController {

    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
