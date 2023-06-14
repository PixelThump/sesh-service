package com.roboter5123.play.backend.game.implementation.chat;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.model.Action;
import com.roboter5123.play.backend.messaging.model.Command;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ChatGame implements Game {

    private final ChatState chatState;
    @Getter
    @Setter
    private GameMode gameMode;
    @Getter
    @Setter
    private String sessionCode;
    private final MessageBroadcaster broadcaster;

    Logger logger;

    public ChatGame(MessageBroadcaster broadcaster) {

        this.broadcaster = broadcaster;
        this.chatState = new ChatState();
        this.gameMode = GameMode.CHAT;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Map<String, Object> joinGame(final String playerName) {

        this.chatState.join(playerName);
        final Command joinCommand = new Command("server", new ChatJoinAction(playerName));
        this.broadcast(joinCommand);

        return this.chatState.getState();
    }

    @Override
    public void broadcast(final Object payload) {

        this.broadcaster.broadcastGameUpdate(this.sessionCode, payload);
    }

    @Override
    public void addCommand(final Command command) throws UnsupportedOperationException {

        Action action = command.getAction();

        if (action instanceof ChatMessageAction chatMessageAction) {

            addMessage(command.getPlayer(), chatMessageAction);

        } else {

            String errorMessage = "Could not execute action. Unsupported action type";
            logger.error(errorMessage);
            throw new UnsupportedOperationException(errorMessage);
        }
    }

    private void addMessage(String playerName, ChatMessageAction action) {

        final String message = this.chatState.addMessage(playerName, action.getMessage());
        this.broadcast(message);
    }
}
