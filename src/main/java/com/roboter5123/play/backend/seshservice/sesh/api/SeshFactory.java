package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;

public interface SeshFactory {

    Sesh createSesh(String seshCode, SeshType seshType) throws UnsupportedOperationException;
}
