package com.roboter5123.play.backend.sesh.implementation;
import com.roboter5123.play.backend.sesh.api.Sesh;
import com.roboter5123.play.backend.sesh.api.SeshFactory;
import com.roboter5123.play.backend.sesh.api.SeshType;
import com.roboter5123.play.backend.sesh.implementation.chat.ChatSesh;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import org.springframework.stereotype.Component;

@Component
public class SeshFactoryImpl implements SeshFactory {

    private final MessageBroadcaster broadcaster;

    public SeshFactoryImpl(MessageBroadcaster broadcaster) {

        this.broadcaster = broadcaster;
    }

    @Override
    public Sesh createSesh(SeshType seshType) throws UnsupportedOperationException {

        final Sesh sesh;

        if (seshType == SeshType.CHAT) {

            sesh = new ChatSesh(broadcaster);

        } else {

            throw new UnsupportedOperationException("No sesh of seshtype " + seshType.name() + " is supported.");
        }

        return sesh;
    }
}
