package com.roboter5123.backend.game.chat;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.JoinUpdate;
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
        state.setLastCommand(new ChatMessageCommand("server", new ChatMessageAction(ChatActionType.JOIN, playerName)));

        JoinUpdate expected = new ChatJoinUpdate();
        expected.setGameState(state);
        expected.setJoinCommand(state.getLastCommand());
        JoinUpdate result = chat.joinGame(playerName);
        assertEquals(expected.getGameState(), result.getGameState());
        assertEquals(expected.getJoinCommand(),result.getJoinCommand());
    }
}