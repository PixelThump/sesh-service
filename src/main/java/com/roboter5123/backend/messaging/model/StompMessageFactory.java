package com.roboter5123.backend.messaging.model;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import org.springframework.stereotype.Component;

@Component
public class StompMessageFactory {

    public StompMessage getMessage(Object payload) throws UnsupportedOperationException {

        StompMessage message;

        if (payload instanceof Command command) {

            message = getMessage(command);

        } else if (payload instanceof GameState gameState) {

            message = getMessage(gameState);

        } else if (payload instanceof RuntimeException exception) {

            message = getMessage(exception);

        } else {

            throw new UnsupportedOperationException();
        }

        return message;
    }

    private CommandStompMessage getMessage(Command command) {

        CommandStompMessage message = new CommandStompMessage();
        message.setCommand(command);
        return message;
    }

    private StateStompMessage getMessage(GameState gameState) {

        StateStompMessage message = new StateStompMessage();
        message.setState(gameState);
        return message;
    }

    private ErrorStompMessage getMessage(RuntimeException exception) {

        ErrorStompMessage message = new ErrorStompMessage();
        message.setError(exception.getMessage());
        return message;
    }
}
