package com.roboter5123.play.backend.game.implementation.chat;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.model.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatGameTest {

    Game chat;
    String playerName;
    MessageBroadcaster broadcaster;

    @BeforeEach
    void setUp() {

        broadcaster = Mockito.mock(MessageBroadcaster.class);
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