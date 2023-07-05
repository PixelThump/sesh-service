package com.roboter5123.play.backend.seshservice.service.api;
import com.roboter5123.play.backend.seshservice.messaging.model.message.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

import java.util.Map;

public interface SeshService {

    /**
     * @param seshType The type of sesh that should be created.
     * @return The sesh that was created.
     * @throws TooManySeshsException Thrown if there are too many seshs already and the requested sesh could not be created.
     */
    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    /**
     * @param seshCode The seshCode of the sesh the client wants to join as host.
     * @param socketId Gotten from message headers. Used to set the playerId. Playerid is used in commands.
     * @return The state of the sesh.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     * @throws PlayerAlreadyJoinedException Thrown if the player has already joined this sesh.
     */
    Map<String, Object> joinSeshAsHost(String seshCode, String socketId) throws NoSuchSeshException, PlayerAlreadyJoinedException;

    /**
     * @param playerName The playerName the client wants to have.
     * @param seshCode The seshCode of the sesh the client wants to join.
     * @param socketId Gotten from message headers. Used to set the playerId. Playerid is used in commands.
     * @return The state of the sesh.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     * @throws PlayerAlreadyJoinedException Thrown if the player has already joined this sesh.
     * @throws SeshCurrentlyNotJoinableException Thrown if the sesh is currently not joinable.
     */
    Map<String, Object> joinSeshAsController(String seshCode, String playerName, String socketId) throws NoSuchSeshException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

    /**
     * @param seshCode The seshcode of the sesh that should be gotten.
     * @return The found sesh with the specified seshCode.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     */
    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    /**
     * @param message The message the client wants to send to the sesh.
     * @param seshCode The seshCode of the sesh the client wants to message.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     * @throws UnsupportedOperationException Thrown if the message is acommand that is not supported be the specific sesh.
     */
    void sendCommandToSesh(CommandStompMessage message, String seshCode) throws NoSuchSeshException, UnsupportedOperationException;
}
