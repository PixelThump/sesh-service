package com.roboter5123.play.backend.seshservice.api.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.StompMessage;
import com.roboter5123.play.backend.seshservice.api.api.StompController;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StompControllerImpl implements StompController {

    private final SeshService seshService;
    private final StompMessageFactory messageFactory;

    @Autowired
    public StompControllerImpl(final SeshService seshService, final StompMessageFactory messageFactory) {

        this.seshService = seshService;
        this.messageFactory = messageFactory;
    }

    @Override
    @SubscribeMapping("/topic/sesh/{seshCode}")
    public StompMessage joinSesh(@Header final String playerName, @DestinationVariable final String seshCode) {

        final Map<String, Object> reply;

        try {

            reply = seshService.joinSesh(seshCode, playerName);

        } catch (NoSuchSeshException e) {

            return messageFactory.getMessage(e);
        }

        return messageFactory.getMessage(reply);
    }

    @Override
    @MessageMapping("/topic/sesh/{seshCode}")
    public StompMessage sendCommandToSesh(final CommandStompMessage message, @DestinationVariable final String seshCode){

        try {

            this.seshService.sendCommandToSesh(message, seshCode);
            return messageFactory.getAckMessage();

        }catch (NoSuchSeshException | UnsupportedOperationException e){

            return messageFactory.getMessage(e);
        }
    }
}
