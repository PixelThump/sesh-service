package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface SeshService {

    /**
     * @param seshType The type of sesh that should be created.
     * @return The sesh that was created.
     * @throws ResponseStatusException Thrown if there are too many seshs already and the requested sesh could not be created.
     */
    Sesh createSesh(String seshType) throws ResponseStatusException;

    /**
     * @param seshCode The seshcode of the sesh that should be gotten.
     * @return The found sesh with the specified seshCode.
     * @throws ResponseStatusException Thrown if there is no sesh with the given seshcode.
     */
    Sesh getSesh(String seshCode) throws ResponseStatusException;

    List<SeshType> getSeshtypes();
}
