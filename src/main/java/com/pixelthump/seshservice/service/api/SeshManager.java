package com.pixelthump.seshservice.service.api;
import com.pixelthump.seshservice.service.exception.NoSuchSeshException;
import com.pixelthump.seshservice.service.exception.TooManySeshsException;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.model.SeshType;

public interface SeshManager {

    /**
     * @param seshCode The seshcode of the sesh that should be gotten.
     * @return The found sesh with the specified seshCode.
     * @throws NoSuchSeshException Thrown if there is no sesh with the given seshcode.
     */
    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    /**
     * @param seshType The type of sesh that should be created.
     * @return The sesh that was created.
     * @throws TooManySeshsException Thrown if there are too many seshs already and the requested sesh could not be created.
     */
    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    /**
     * Clears seshs. Implementations can vary.
     * But currently it deletes seshs that haven#t been interacted with for 10 minutes.
     */
    void clearSeshs();
}
