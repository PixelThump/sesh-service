package com.pixelthump.seshservice.sesh.api;
import com.pixelthump.seshservice.sesh.model.SeshType;

public interface SeshFactory {

    Sesh createSesh(String seshCode, SeshType seshType) throws UnsupportedOperationException;
}
