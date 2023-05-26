package com.roboter5123.backend.game.api;
import com.roboter5123.backend.service.api.GameService;

public interface GameFactory {

    Game createGame(GameMode gameMode, GameService service) throws UnsupportedOperationException;
}
