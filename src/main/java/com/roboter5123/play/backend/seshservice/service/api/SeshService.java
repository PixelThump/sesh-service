package com.roboter5123.play.backend.seshservice.service.api;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;

import java.util.Map;

public interface SeshService {

    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    Map<String, Object> joinSesh(String seshCode, String playerName) throws NoSuchSeshException;

    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    void sendCommandToSesh(CommandStompMessage message, String seshCode) throws NoSuchSeshException, UnsupportedOperationException;
}