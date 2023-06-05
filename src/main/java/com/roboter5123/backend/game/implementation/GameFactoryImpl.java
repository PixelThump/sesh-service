package com.roboter5123.backend.game.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameFactory;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import org.springframework.stereotype.Component;

@Component
public class GameFactoryImpl implements GameFactory {

    private final MessageBroadcaster broadcaster;

    public GameFactoryImpl(MessageBroadcaster broadcaster) {

        this.broadcaster = broadcaster;
    }

    @Override
    public Game createGame(GameMode gameMode) throws UnsupportedOperationException {

        final Game game;

        if (gameMode == GameMode.CHAT) {

            game = new ChatGame(broadcaster);

        } else {

            throw new UnsupportedOperationException("No game of game mode " + gameMode.name() + "is supported.");
        }

        return game;
    }

}
