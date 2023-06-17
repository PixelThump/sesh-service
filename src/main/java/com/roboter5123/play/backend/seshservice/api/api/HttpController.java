package com.roboter5123.play.backend.seshservice.api.api;
import com.roboter5123.play.backend.seshservice.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.api.model.HttpSeshDTO;
import com.roboter5123.play.backend.seshservice.api.model.exception.NoSuchSeshHttpException;
import com.roboter5123.play.backend.seshservice.api.model.exception.TooManySeshsHttpException;

import java.util.List;

public interface HttpController {

    List<SeshType> getSeshTypes();

    HttpSeshDTO createSesh(String seShTypeString) throws TooManySeshsHttpException, BadRequestException;

    HttpSeshDTO getSesh(String seshCode) throws NoSuchSeshHttpException;
}
