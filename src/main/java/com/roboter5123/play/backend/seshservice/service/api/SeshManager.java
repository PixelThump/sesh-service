package com.roboter5123.play.backend.seshservice.service.api;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

public interface SeshManager {

    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    Sesh createSesh(SeshType seshType) throws TooManySeshsException;

    void clearSeshs();
}
