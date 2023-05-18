package com.roboter5123.backend.service.model;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import org.springframework.stereotype.Component;

@Component
public class StompMessageFactory {


    public  CommandStompMessage getMessage(Command command){

        CommandStompMessage message = new CommandStompMessage();
        message.setMessageType(StompMessageType.COMMAND);
        message.setBody(command);
        return message;
    }

    public StateStompMessage getMessage(GameState gameState) {

        StateStompMessage message = new StateStompMessage();
        message.setMessageType(StompMessageType.STATE);
        message.setBody(gameState.getState());
        return message;
    }

    public ErrorStompMessage getMessage(RuntimeException exception) {

        ErrorStompMessage message = new ErrorStompMessage();
        message.setMessageType(StompMessageType.ERROR);
        message.setBody(exception.getMessage());
        return message;
    }
}
