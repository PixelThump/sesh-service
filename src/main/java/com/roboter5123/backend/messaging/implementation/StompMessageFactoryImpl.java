package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.messaging.api.StompMessageFactory;
import com.roboter5123.backend.messaging.model.CommandStompMessage;
import com.roboter5123.backend.messaging.model.ErrorStompMessage;
import com.roboter5123.backend.messaging.model.StateStompMessage;
import com.roboter5123.backend.messaging.model.StompMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StompMessageFactoryImpl implements StompMessageFactory {

    public StompMessage getMessage(Object payload) throws UnsupportedOperationException {

        final StompMessage message;

        if (payload instanceof Command command) {

            message = getMessage(command);

        } else if (payload instanceof RuntimeException exception) {

            message = getMessage(exception);

        } else {

            throw new UnsupportedOperationException();
        }

        return message;
    }

    private CommandStompMessage getMessage(Command command) {

        final CommandStompMessage message = new CommandStompMessage();
        message.setCommand(command);
        return message;
    }

    public StateStompMessage getMessage(Map<String, Object> gameState) {

        final StateStompMessage message = new StateStompMessage();
        message.setState(gameState);
        return message;
    }

    private ErrorStompMessage getMessage(RuntimeException exception) {

        final ErrorStompMessage message = new ErrorStompMessage();
        message.setError(exception.getMessage());
        return message;
    }
}