package com.roboter5123.play.backend.seshservice.sesh.api;
public interface SeshFactory {

    Sesh createSesh(String seshCode, SeshType seshType) throws UnsupportedOperationException;
}
