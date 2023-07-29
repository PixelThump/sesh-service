package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.model.Sesh;

public interface SeshFactory {

    Sesh createSesh(String seshCode, String seshType);
}
