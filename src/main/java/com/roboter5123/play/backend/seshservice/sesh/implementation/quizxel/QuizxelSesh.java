package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Scope("prototype")
public class QuizxelSesh extends AbstractSeshBaseClass {

    private static final Integer MAXPLAYERS = 5;

    public QuizxelSesh(MessageBroadcaster broadcaster) {

        super(broadcaster, SeshType.QUIZXEL, new QuizxelPlayerManager(MAXPLAYERS), MAXPLAYERS);
    }

    @Override
    protected Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("players", this.playerManager.getPlayers());
        state.put("maxPlayers", MAXPLAYERS);
        state.put("currentStage", this.currentStage);

        return state;
    }

    @Override
    protected void processMainCommand(Command command) {

        throw new UnsupportedOperationException(command.toString());
    }

}
