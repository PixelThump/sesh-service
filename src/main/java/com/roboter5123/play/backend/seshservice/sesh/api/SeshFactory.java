package com.roboter5123.play.backend.seshservice.sesh.api;
public interface SeshFactory {

    Sesh createSesh(SeshType seshType) throws UnsupportedOperationException;
}
