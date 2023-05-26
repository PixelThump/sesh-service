package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.JoinUpdate;
import lombok.Getter;

public class ChatGame implements Game {

    private final ChatState chatState;

    @Getter
    private final GameMode gameMode;

    public ChatGame() {

        this.chatState = new ChatState();
        this.gameMode = GameMode.CHAT;
    }

    @Override
    public JoinUpdate joinGame(final String playerName) {

        this.chatState.join(playerName);
        final Command joinCommand = new Command("server", new ChatJoinAction(playerName));

        final JoinUpdate joinUpdate = new JoinUpdate();
        joinUpdate.setGameState(this.chatState);
        joinUpdate.setCommand(joinCommand);

        return joinUpdate;
    }
}
