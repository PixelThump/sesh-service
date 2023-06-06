package com.roboter5123.play.backend.game.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameFactory;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.messaging.api.MessageBroadcaster;
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
