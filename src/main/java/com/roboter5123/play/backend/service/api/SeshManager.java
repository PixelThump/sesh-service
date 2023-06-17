package com.roboter5123.play.backend.service.api;
import com.roboter5123.play.backend.sesh.api.Sesh;
import com.roboter5123.play.backend.sesh.api.SeshType;
import com.roboter5123.play.backend.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.service.exception.TooManySeshsException;

public interface SeshManager {

    Sesh getSesh(String seshCode) throws NoSuchSeshException;

    Sesh createSesh(SeshType seshType) throws TooManySeshsException;
}
