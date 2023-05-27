package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.ServiceCommandStompMessage;
import com.roboter5123.backend.service.model.ErrorStompMessage;
import com.roboter5123.backend.service.model.StateStompMessage;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.ServiceCommand;
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
