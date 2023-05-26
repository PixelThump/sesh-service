package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.JoinUpdate;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import com.roboter5123.backend.game.implementation.chat.ChatJoinAction;
import com.roboter5123.backend.game.implementation.chat.ChatState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatGameTest {

    Game chat;

    String playerName;

    @BeforeEach
    void setUp() {

        chat = new ChatGame();
        playerName = "roboter5123";
    }

    @Test
    void joinGame() {

        ChatState state = new ChatState();
        state.getChatter().add(playerName);
        state.getChatLog().add(playerName + " joined the Conversation");
        state.setLastCommand(new Command("server", new ChatJoinAction(playerName)));

        JoinUpdate expected = new JoinUpdate();
        expected.setGameState(state);
        expected.setCommand(state.getLastCommand());
        JoinUpdate result = chat.joinGame(playerName);
        assertEquals(expected.getGameState(), result.getGameState());
        assertEquals(expected.getCommand(),result.getCommand());
    }
}