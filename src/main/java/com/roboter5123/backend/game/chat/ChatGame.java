package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.JoinUpdate;
import com.roboter5123.backend.service.GameService;

public class ChatGame implements Game {

    ChatState chatState;

    private final GameService service;
    private GameMode gameMode;

    public ChatGame(GameService service) {

        this.chatState = new ChatState();
        this.service = service;


    }

    @Override
    public JoinUpdate joinGame(String playerName) {

        this.chatState.join(playerName);
        Command joinCommand = new ChatMessageCommand("server", new ChatMessageAction("MESSAGE", playerName + " has joined the conversation!"));

        JoinUpdate joinUpdate = new ChatJoinUpdate();
        joinUpdate.setGameState(this.chatState);
        joinUpdate.setJoinCommand(joinCommand);

        return joinUpdate;
    }

    @Override
    public void setGamemode(GameMode gameMode) {

        this.gameMode = gameMode;
    }

    @Override
    public GameMode getGameMode() {

        return this.gameMode;
    }
}
