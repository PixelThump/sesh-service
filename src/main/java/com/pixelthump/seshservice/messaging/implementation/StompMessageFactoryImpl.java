package com.pixelthump.seshservice.messaging.implementation;
import com.pixelthump.seshservice.messaging.api.StompMessageFactory;
import com.pixelthump.seshservice.messaging.model.Command;
import com.pixelthump.seshservice.messaging.model.message.*;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class StompMessageFactoryImpl implements StompMessageFactory {

    public StompMessage getMessage(Object payload) throws UnsupportedOperationException {

        final StompMessage message;

        if (payload instanceof Command command) {

            message = getMessage(command);

        } else if (payload instanceof RuntimeException exception) {

            message = getMessage(exception);

        } else if (payload instanceof AbstractSeshState state) {

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

    private StateStompMessage getMessage(AbstractSeshState seshState) {

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
