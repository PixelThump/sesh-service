package com.roboter5123.play.backend.seshservice.api.api;
import com.roboter5123.play.backend.seshservice.api.model.HttpSeshDTO;
import com.roboter5123.play.backend.seshservice.api.model.exception.NoSuchSeshHttpException;
import com.roboter5123.play.backend.seshservice.api.model.exception.TooManySeshsHttpException;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

import java.util.List;

public interface HttpController {

    /**
     * @return A list of seshtypes this service supports
     */
    List<SeshType> getSeshTypes();

    /**
     * @param seshTypeString A string of the Enum Seshtype of the type of Seshs that should be created.
     * @return Initialization data for the created sesh.
     * @throws TooManySeshsHttpException May be thrown if the sesh could not be created due to too many sesh being alive.
     */
    HttpSeshDTO createSesh(String seshTypeString) throws TooManySeshsHttpException;

    /**
     * @param seshCode The seshcode for the sesh whose information should be returned
     * @return Initialization data for the created sesh.
     * @throws NoSuchSeshHttpException May be thrown if the sesh could not be found
     */
    HttpSeshDTO getSesh(String seshCode) throws NoSuchSeshHttpException;
}
