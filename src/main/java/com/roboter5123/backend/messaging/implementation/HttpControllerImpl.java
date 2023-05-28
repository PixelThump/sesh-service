package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.api.HttpController;
import com.roboter5123.backend.messaging.model.HttpGame;
import com.roboter5123.backend.messaging.model.exception.NoSuchSessionHttpException;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class HttpControllerImpl implements HttpController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    @Autowired
    public HttpControllerImpl(GameService gameService, ModelMapper modelMapper) {

        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @Override
    @PostMapping("/sessions")
    @ResponseBody
    public String createSession(@RequestBody final GameMode gameMode) throws TooManySessionsException {

        return this.gameService.createSession(gameMode);

    }

    @Override
    @GetMapping("sessions/{sessionCode}")
    public HttpGame getGame(@PathVariable String sessionCode) throws NoSuchSessionHttpException {

        Optional<Game> game =this.gameService.getGame(sessionCode);

        if (game.isEmpty()){

            throw new NoSuchSessionHttpException("No session with code " + sessionCode + " exists!");
        }

        return modelMapper.map(game.get(),HttpGame.class);

    }
}
