package com.roboter5123.play.backend.service.api;
import com.roboter5123.play.backend.sesh.api.Sesh;
import com.roboter5123.play.backend.sesh.api.SeshType;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.service.exception.TooManySeshsException;

import java.util.Map;

public interface SeshService {

    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    Map<String, Object> joinSesh(String seshCode, String playerName) throws NoSuchSeshException;

    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    void sendCommandToSesh(CommandStompMessage message, String seshCode) throws NoSuchSeshException, UnsupportedOperationException;
}
