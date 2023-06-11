package com.roboter5123.play.backend.webinterface.api.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.webinterface.api.api.HttpController;
import com.roboter5123.play.backend.webinterface.api.model.HttpGameDTO;
import com.roboter5123.play.backend.webinterface.api.model.exception.NoSuchSessionHttpException;
import com.roboter5123.play.backend.webinterface.api.model.exception.TooManySessionsHttpException;
import com.roboter5123.play.backend.webinterface.service.api.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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
    public List<GameMode> getGameModes() {

        GameMode[] gameModes = GameMode.values();
        return Arrays.stream(gameModes).filter(gameMode -> gameMode!= GameMode.UNKNOWN).toList();
    }

    @Override
    @PostMapping("/sessions")
    @ResponseBody
    public HttpGameDTO createSession(@RequestBody final GameMode gameMode) throws TooManySessionsHttpException {

        Optional<Game> game = this.gameService.createSession(gameMode);

        if (game.isEmpty()) {

            throw new TooManySessionsHttpException("Unable to create session because there were too many sessions!");
        }

        return modelMapper.map(game, HttpGameDTO.class);
    }

    @Override
    @GetMapping("sessions/{sessionCode}")
    public HttpGameDTO getGame(@PathVariable String sessionCode) throws NoSuchSessionHttpException {

        Optional<Game> game = this.gameService.getGame(sessionCode);

        if (game.isEmpty()) {

            throw new NoSuchSessionHttpException("No session with code " + sessionCode + " exists!");
        }

        return modelMapper.map(game.get(), HttpGameDTO.class);
    }
}
