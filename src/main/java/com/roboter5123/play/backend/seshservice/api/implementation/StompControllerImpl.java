package com.roboter5123.play.backend.seshservice.api.implementation;
import com.roboter5123.play.backend.seshservice.api.api.StompController;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.StompMessage;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@Log4j2
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

        log.info("StompControllerImpl: Entering joinSesh(playerName={} seshCode={})", playerName, seshCode);

        try {

            Map<String, Object> state = seshService.joinSesh(seshCode, playerName);
            StompMessage reply = messageFactory.getMessage(state);

            log.info("StompControllerImpl: Exiting joinSesh(reply={})", reply);
            return reply;

        } catch (NoSuchSeshException | PlayerAlreadyJoinedException e) {

            StompMessage reply = messageFactory.getMessage(e);
            log.error("StompControllerImpl: Exiting joinSesh(reply={})", reply);
            return reply;
        }
    }

    @Override
    @MessageMapping("/topic/sesh/{seshCode}")
    public StompMessage sendCommandToSesh(final CommandStompMessage message, @DestinationVariable final String seshCode) {

        log.info("StompControllerImpl: Entering sendCommandToSesh(message={} seshCode={})", message, seshCode);

        try {

            this.seshService.sendCommandToSesh(message, seshCode);
            StompMessage reply = messageFactory.getAckMessage();

            log.info("StompControllerImpl: Exiting sendCommandToSesh(reply={})", reply);
            return reply;

        } catch (NoSuchSeshException | UnsupportedOperationException e) {

            StompMessage reply = messageFactory.getMessage(e);

            log.error("StompControllerImpl: Exiting sendCommandToSesh(reply={})", reply);
            return reply;
        }
    }
}
