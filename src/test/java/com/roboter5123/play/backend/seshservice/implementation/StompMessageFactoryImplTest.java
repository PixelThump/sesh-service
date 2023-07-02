package com.roboter5123.play.backend.seshservice.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.implementation.StompMessageFactoryImpl;
import com.roboter5123.play.backend.seshservice.messaging.model.*;
import com.roboter5123.play.backend.seshservice.messaging.model.action.BasicAction;
import com.roboter5123.play.backend.seshservice.messaging.model.message.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StompMessageFactoryImplTest {


    StompMessageFactory messageFactory;
    String playerName;
    String errorMessage;

    @BeforeEach
    void setUp() {

        this.messageFactory = new StompMessageFactoryImpl();

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

        Command serviceCommand= new Command(playerName, new BasicAction());

        Command command = new Command();
        command.setAction(new BasicAction());
        command.setPlayerId(playerName);

        CommandStompMessage expected = new CommandStompMessage();
        expected.setCommand(serviceCommand);
        StompMessage result = messageFactory.getMessage(command);
        assertEquals(expected, result);
    }

    @Test
    void GET_MESSAGE_WITH_GAME_STATE_SHOULD_RETURN_GAME_STATE_STOMP_MESSAGE_WITH_GAME_STATE() {

        Map<String, Object> state = new HashMap<>();
        StateStompMessage expected = new StateStompMessage();
        expected.setState(state);
        StompMessage result = messageFactory.getMessage(state);
        assertEquals(expected, result);
    }

    @Test
    void GET_MESSAGE_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        Object o = new Object();
        assertThrows(UnsupportedOperationException.class, () -> messageFactory.getMessage(o));
    }

    @Test
    void GET_ACK_MESSAGE_SHOULD_RETURN_ACK_MESSAGE(){

        StompMessage expected = new GenericStompMessage();
        StompMessage result = messageFactory.getAckMessage();
        assertEquals(expected, result);
    }
}