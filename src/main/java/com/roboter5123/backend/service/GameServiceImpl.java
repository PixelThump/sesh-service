package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.JoinUpdate;
import com.roboter5123.backend.messaging.MessageBroadcaster;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;
import com.roboter5123.backend.service.model.JoinPayloads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    GameSessionManager gameSessionManager;

    private final MessageBroadcaster broadcaster;

    @Autowired
    public GameServiceImpl(GameSessionManager gameSessionManager, MessageBroadcaster broadcaster) {

        this.gameSessionManager = gameSessionManager;
        this.broadcaster = broadcaster;

//        Todo: Remove in production
//        gameSessionManager.createGameSession(GameMode.CHAT, this);
    }

    @Override
    public String createSession(GameMode gameMode) throws TooManySessionsException {

        return this.gameSessionManager.createGameSession(gameMode, this);
    }

    @Override
    public JoinPayloads joinGame(String sessionCode, String playerName) throws NoSuchSessionException {

        Game game = this.gameSessionManager.getGameSession(sessionCode);

        JoinUpdate joinUpdate = game.joinGame(playerName);

        JoinPayloads payloads = new JoinPayloads();
        payloads.setReply(joinUpdate.getGameState());
        payloads.setBroadcast(joinUpdate.getJoinCommand());

        return payloads;
    }

    @Override
    public void broadcastGameUpdate(String sessionCode, Object payload) {

        this.broadcaster.broadcastGameUpdate(sessionCode,payload);
    }

}
