package com.pixelthump.seshservice.service.api;
import com.pixelthump.seshservice.messaging.model.message.CommandStompMessage;
import com.pixelthump.seshservice.service.exception.NoSuchSeshException;
import com.pixelthump.seshservice.service.exception.TooManySeshsException;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.pixelthump.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import com.pixelthump.seshservice.sesh.model.SeshType;

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
    AbstractSeshState joinSeshAsHost(String seshCode, String socketId) throws NoSuchSeshException, PlayerAlreadyJoinedException;

    /**
     * @param playerName The playerName the client wants to have.
     * @param seshCode The seshCode of the sesh the client wants to join.
     * @param socketId Gotten from message headers. Used to set the playerId. Playerid is used in commands.
     * @return The state of the sesh.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     * @throws PlayerAlreadyJoinedException Thrown if the player has already joined this sesh.
     * @throws SeshCurrentlyNotJoinableException Thrown if the sesh is currently not joinable.
     */
    AbstractSeshState joinSeshAsController(String seshCode, String playerName, String socketId) throws NoSuchSeshException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException;

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
