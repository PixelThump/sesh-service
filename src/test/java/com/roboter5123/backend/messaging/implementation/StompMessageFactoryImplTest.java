package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.messaging.model.Command;
import com.roboter5123.backend.game.implementation.chat.ChatMessageAction;
import com.roboter5123.backend.game.implementation.chat.ChatState;
import com.roboter5123.backend.messaging.api.StompMessageFactory;
import com.roboter5123.backend.messaging.model.ErrorStompMessage;
import com.roboter5123.backend.messaging.model.ServiceCommandStompMessage;
import com.roboter5123.backend.messaging.model.StateStompMessage;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.ServiceCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class StompMessageFactoryImplTest {

    @Autowired
    StompMessageFactory messageFactory;
    String playerName;
    String errorMessage;

    @BeforeEach
    void setUp() {

        errorMessage = "This is an error!";
        playerName = "roboter5123";
    }

    @Test
    void GET_MESSAGE_WITH_EXCEPTION_SHOULD_RETURN_ERROR_STOMP_MESSAGE_WITH_EXCEPTION_MESSAGE() {

        Exception exception = new RuntimeException(errorMessage);
        ErrorStompMessage expected = new ErrorStompMessage();
        expected.setError(exception.getMessage());
        StompMessage result = messageFactory.getMessage(exception);
        assertEquals(expected, result);
    }

    @Test
    void GET_MESSAGE_WITH_COMMAND_SHOULD_RETURN_COMMAND_STOMP_MESSAGE_WITH_COMMAND() {

        ServiceCommand serviceCommand= new ServiceCommand(playerName, new ChatMessageAction());

        Command command = new Command();
        command.setAction(new ChatMessageAction());
        command.setPlayer(playerName);

        ServiceCommandStompMessage expected = new ServiceCommandStompMessage();
        expected.setCommand(serviceCommand);
        StompMessage result = messageFactory.getMessage(command);
        assertEquals(expected, result);
    }

    @Test
    void GET_MESSAGE_WITH_GAME_STATE_SHOULD_RETURN_GAME_STATE_STOMP_MESSAGE_WITH_GAME_STATE() {

        ChatState state = new ChatState();
        StateStompMessage expected = new StateStompMessage();
        expected.setState(state.getState());
        StompMessage result = messageFactory.getMessage(state.getState());
        assertEquals(expected, result);
    }

    @Test
    void GET_MESSAGE_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        Object o = new Object();
        assertThrows(UnsupportedOperationException.class, () -> messageFactory.getMessage(o));
    }
}