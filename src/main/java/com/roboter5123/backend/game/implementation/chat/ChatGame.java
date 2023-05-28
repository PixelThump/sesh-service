package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.api.JoinUpdate;
import lombok.Getter;
import lombok.Setter;

public class ChatGame implements Game {

    private final ChatState chatState;

    @Getter
    @Setter
    private GameMode gameMode;

    public ChatGame() {

        this.chatState = new ChatState();
        this.gameMode = GameMode.CHAT;
    }

    @Override
    public JoinUpdate joinGame(final String playerName) {

        this.chatState.join(playerName);
        final Command joinCommand = new Command("server", new ChatJoinAction(playerName));

        final JoinUpdate joinUpdate = new JoinUpdate();
        joinUpdate.setGameState(this.chatState.getState());
        joinUpdate.setCommand(joinCommand);

        return joinUpdate;
    }
}
