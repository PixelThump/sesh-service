package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.springframework.stereotype.Component;

@Component
public class SeshFactoryImpl implements SeshFactory {

    private final MessageBroadcaster broadcaster;

    public SeshFactoryImpl(MessageBroadcaster broadcaster) {

        this.broadcaster = broadcaster;
    }

    @Override
    public Sesh createSesh(String seshCode, SeshType seshType) throws UnsupportedOperationException {

        final Sesh sesh;

        if (seshType == SeshType.QUIZXEL) {

            sesh = new QuizxelSesh(seshCode, broadcaster);

        } else {

            throw new UnsupportedOperationException("No sesh of seshtype " + seshType.name() + " is supported.");
        }

        return sesh;
    }
}
