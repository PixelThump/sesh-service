package com.roboter5123.play.backend.seshservice.messaging.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.message.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class StompMessageFactoryImpl implements StompMessageFactory {

    public StompMessage getMessage(Object payload) throws UnsupportedOperationException {

        final StompMessage message;

        if (payload instanceof Command command) {

            message = getMessage(command);

        } else if (payload instanceof RuntimeException exception) {

            message = getMessage(exception);

        } else if (payload instanceof Map<?, ?> genericState) {

            Map<String, Object> state = (Map<String, Object>) genericState;
            message = getMessage(state);
        } else {

            String errorMessage = "Could not create StompMessage. Unsupported payload type";
            log.error(errorMessage);
            throw new UnsupportedOperationException(errorMessage);
        }

        return message;
    }

    private CommandStompMessage getMessage(Command command) {

        final CommandStompMessage message = new CommandStompMessage();
        message.setCommand(command);
        return message;
    }

    private StateStompMessage getMessage(Map<String, Object> seshState) {

        final StateStompMessage message = new StateStompMessage();
        message.setState(seshState);
        return message;
    }

    @Override
    public StompMessage getAckMessage() {

        return new GenericStompMessage();
    }

    private ErrorStompMessage getMessage(RuntimeException exception) {

        final ErrorStompMessage message = new ErrorStompMessage();
        message.setError(exception.getMessage());
        return message;
    }
}
