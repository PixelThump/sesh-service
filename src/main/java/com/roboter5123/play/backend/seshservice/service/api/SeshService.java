package com.roboter5123.play.backend.seshservice.service.api;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;

import java.util.Map;

public interface SeshService {

    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    Map<String, Object> joinSeshAsHost(String seshCode)throws NoSuchSeshException, PlayerAlreadyJoinedException;

    Map<String, Object> joinSeshAsController(String seshCode, String playerName)throws NoSuchSeshException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    void sendCommandToSesh(CommandStompMessage message, String seshCode) throws NoSuchSeshException, UnsupportedOperationException;
}
