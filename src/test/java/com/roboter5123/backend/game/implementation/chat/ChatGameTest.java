package com.roboter5123.backend.game.implementation.chat;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.messaging.api.MessageBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ChatGameTest {

    Game chat;

    String playerName;

    @MockBean
    MessageBroadcaster broadcaster;

    @BeforeEach
    void setUp() {

        chat = new ChatGame(broadcaster);
        playerName = "roboter5123";
    }

    @Test
    void joinGame() {

        ChatState state = new ChatState();
        state.getChatters().add(playerName);
        state.getChatLog().add(playerName + " joined the Conversation");
        state.setLastCommand(new Command("server", new ChatJoinAction(playerName)));

        Map<String, Object> expected = state.getState();
        Map<String, Object> result = chat.joinGame(playerName);
        assertEquals(expected, result);

    }
}