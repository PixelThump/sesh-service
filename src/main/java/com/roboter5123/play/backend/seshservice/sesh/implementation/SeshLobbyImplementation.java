package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.messaging.model.action.MakeVIPAction;
import com.roboter5123.play.backend.seshservice.messaging.model.action.StartSeshAction;
import com.roboter5123.play.backend.seshservice.sesh.api.PlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshLobby;

public class SeshLobbyImplementation implements SeshLobby {

    private final PlayerManager playerManager;

    public SeshLobbyImplementation(PlayerManager playerManager) {

        this.playerManager = playerManager;
    }

    @Override
    public boolean processLobbyCommand(Command command) {

        String playerId = command.getPlayerId();
        Action action = command.getAction();

        if (this.playerManager.isVIP(playerId) && action instanceof StartSeshAction) {

            return true;

        } else if ((this.playerManager.isVIP(playerId) || !this.playerManager.hasVIP()) && action instanceof MakeVIPAction makeVIPAction) {

            this.playerManager.setVIP(makeVIPAction.getTargetId());
        }

        return false;
    }
}
