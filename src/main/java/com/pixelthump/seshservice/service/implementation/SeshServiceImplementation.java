package com.pixelthump.seshservice.service.implementation;
import com.pixelthump.seshservice.messaging.model.message.CommandStompMessage;
import com.pixelthump.seshservice.service.exception.NoSuchSeshException;
import com.pixelthump.seshservice.service.api.SeshManager;
import com.pixelthump.seshservice.service.api.SeshService;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import com.pixelthump.seshservice.sesh.model.SeshType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AbstractSeshState joinSeshAsHost(String seshCode, String socketId) {

        final Sesh sesh = this.getSesh(seshCode);
        return sesh.joinSeshAsHost(socketId);
    }

    @Override
    public AbstractSeshState joinSeshAsController(String seshCode, String playerName, String socketId) {

        final Sesh sesh = this.getSesh(seshCode);
        return sesh.joinSeshAsController(playerName, socketId);
    }
}