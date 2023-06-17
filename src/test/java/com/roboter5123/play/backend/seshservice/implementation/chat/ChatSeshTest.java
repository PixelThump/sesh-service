package com.roboter5123.play.backend.seshservice.implementation.chat;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatJoinAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatMessageAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatSesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class ChatSeshTest {

    Sesh chat;
    String playerName;
    String sessioncode;
    @MockBean
    MessageBroadcaster broadcaster;

    @BeforeEach
    void setUp() {

        broadcaster = Mockito.mock(MessageBroadcaster.class);
        chat = new ChatSesh(broadcaster);
        sessioncode = "abcd";
        chat.setSeshCode(sessioncode);
        playerName = "roboter5123";
    }

    @Test
    void joinGame_should_return_gamestate_as_map_string_object() {

        ChatState state = new ChatState();
        state.getChatters().add(playerName);
        state.getChatLog().add(playerName + " joined the Conversation");
        state.setLastCommand(new Command("server", new ChatJoinAction(playerName)));

        Map<String, Object> expected = state.getState();
        Map<String, Object> result = chat.joinSesh(playerName);
        assertEquals(expected, result);
    }

    @Test
    void addCommand_should_broadcast_message_and_add_message_to_log(){

        ChatMessageAction incomingAction = new ChatMessageAction("Hello World!");
        Command incomingCommand = new Command(playerName,incomingAction);
        chat.addCommand(incomingCommand);
        verify(broadcaster).broadcastSeshUpdate(sessioncode, playerName + ": " + incomingAction.getMessage());
    }
}