package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.model.AbstractSeshState;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

import java.time.LocalDateTime;

public interface Sesh {

    /**
     * @param socketId Used to set the playerId. Playerid is used in commands.
     * @return The state of the sesh
     * @throws PlayerAlreadyJoinedException Thrown if the player has already joined this sesh.
     */
    AbstractSeshState joinSeshAsHost(String socketId) throws PlayerAlreadyJoinedException;

    /**
     * @param playerName The playerName the client wants to have.
     * @param socketId   Used to set the playerId. Playerid is used in commands.
     * @return The state of the sesh.
     * @throws NoSuchSeshException               Thrown if there is no sesh with the given seshcode.
     * @throws PlayerAlreadyJoinedException      Thrown if the player has already joined this sesh.
     * @throws SeshCurrentlyNotJoinableException Thrown if the sesh is currently not joinable.
     */
    AbstractSeshState joinSeshAsController(String playerName, String socketId) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

    /**
     * @param command The command the client wants the sesh to execute.
     * @throws PlayerNotInSeshException Thrown if the player hasn't joined the sesh before sending this command.
     */
    void addCommand(Command command) throws PlayerNotInSeshException;

    void startSesh();

    LocalDateTime getLastInteractionTime();

    SeshStage getCurrentStage();

    SeshType getSeshType();

    String getSeshCode();

    void setSeshCode(String seshCode);
}
