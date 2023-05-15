package com.roboter5123.backend.service;
import java.util.List;

public interface GameService {

    List<String> joinGame(String gameCode, String playerName);
}
