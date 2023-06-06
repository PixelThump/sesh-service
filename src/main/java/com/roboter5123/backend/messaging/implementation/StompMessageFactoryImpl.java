package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.messaging.model.Command;
import com.roboter5123.backend.messaging.model.*;
import com.roboter5123.backend.messaging.api.StompMessageFactory;
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

    private ServiceCommandStompMessage getMessage(Command command) {

        final ServiceCommand serviceCommand = new ServiceCommand();
        serviceCommand.setAction(command.getAction());
        serviceCommand.setPlayer(command.getPlayer());

        final ServiceCommandStompMessage message = new ServiceCommandStompMessage();
        message.setCommand(serviceCommand);
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
