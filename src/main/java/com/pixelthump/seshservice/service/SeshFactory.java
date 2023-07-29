package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;

public interface SeshFactory {

    Sesh createSesh(String seshCode, SeshType seshType);
}
