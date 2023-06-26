package com.roboter5123.play.backend.seshservice.service.implementation;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeshServiceImpl implements SeshService {

    private final SeshManager seshManager;

    @Autowired
    public SeshServiceImpl(SeshManager seshManager) {

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
    public Map<String, Object> joinSesh(String seshCode, String playerName) throws NoSuchSeshException, PlayerAlreadyJoinedException {

        final Sesh sesh = this.getSesh(seshCode);
        return sesh.joinSesh(playerName);

    }
}
