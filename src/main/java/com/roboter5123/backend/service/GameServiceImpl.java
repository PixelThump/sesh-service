package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.messaging.MessagingController;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.GameMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    GameSessionManager gameSessionManager;
    MessagingController messageController;

    @Autowired
    public GameServiceImpl(GameSessionManager gameSessionManager, MessagingController messageController) {

        this.gameSessionManager = gameSessionManager;
        this.messageController = messageController;

//        Todo: Remove in production
        gameSessionManager.createGameSession(GameMode.CHAT);
    }

    @Override
    public Map<String, Object> joinGame(String gameCode, String playerName) throws NoSuchSessionException {

        Game game = this.gameSessionManager.getGameSession(gameCode);

        GameState state = game.joinGame(playerName);
        //        TODO: Rework how i get the join command
        Command joinCommand = state.getLastCommand();

        Map<String,Object> payloads = new HashMap<>();
        payloads.put("reply", state);
        payloads.put("broadcast", joinCommand);

        return payloads;
    }

    @Override
    public void broadcastPeriodicGameUpdate(String sessionCode, Object payload) {

        this.messageController.broadcast(sessionCode, payload);
    }

}
