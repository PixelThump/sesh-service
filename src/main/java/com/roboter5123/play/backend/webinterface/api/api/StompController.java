package com.roboter5123.play.backend.webinterface.api.api;
import com.roboter5123.play.backend.webinterface.service.exception.NoSuchSessionException;
import com.roboter5123.play.messaging.model.StompMessage;

public interface StompController {

    StompMessage joinSession(String playerName, String sessionCode) throws NoSuchSessionException;
}
