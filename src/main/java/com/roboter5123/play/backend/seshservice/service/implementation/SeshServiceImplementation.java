package com.roboter5123.play.backend.seshservice.service.implementation;
import com.roboter5123.play.backend.seshservice.messaging.model.message.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeshServiceImplementation implements SeshService {

    private final SeshManager seshManager;

    @Autowired
    public SeshServiceImplementation(final SeshManager seshManager) {

        this.seshManager = seshManager;
    }

    @Override
    public Sesh createSesh(SeshType seshType) {

        return this.seshManager.createSesh(seshType);
    }

    @Override
    public Sesh getSesh(String seshCode) throws NoSuchSeshException, UnsupportedOperationException {

        return this.seshManager.getSesh(seshCode);
    }

    @Override
    public void sendCommandToSesh(final CommandStompMessage message, final String seshCode) throws NoSuchSeshException {

        final Sesh sesh = getSesh(seshCode);
        sesh.addCommand(message.getCommand());
    }

    @Override
    public Map<String, Object> joinSeshAsHost(String seshCode) {

        final Sesh sesh = this.getSesh(seshCode);
        return sesh.joinSeshAsHost();
    }

    @Override
    public Map<String, Object> joinSeshAsController(String seshCode, String playerName) {

        final Sesh sesh = this.getSesh(seshCode);
        return sesh.joinSeshAsController(playerName);
    }
}
