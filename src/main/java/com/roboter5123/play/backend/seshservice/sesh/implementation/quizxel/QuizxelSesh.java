package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@ToString
public class QuizxelSesh extends AbstractSeshBaseClass {

    public QuizxelSesh(String seshCode, MessageBroadcaster broadcaster) {

        super(seshCode, broadcaster, SeshType.QUIZXEL);
    }

    @Override
    public Map<String, Object> joinSesh(String playerName) throws PlayerAlreadyJoinedException {

//        TODO: Implement
        return new HashMap<>();
    }

    @Override
    public void addCommand(Command command) throws UnsupportedOperationException {

//        TODO: Implement
    }
}
