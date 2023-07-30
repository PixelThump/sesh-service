package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.SeshTypeRepository;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class SeshTypeServiceImpl implements SeshTypeService {

    private final SeshTypeRepository seshTypeRepository;

    @Autowired
    public SeshTypeServiceImpl(SeshTypeRepository seshTypeRepository) {

        this.seshTypeRepository = seshTypeRepository;
    }

    @Override
    public List<SeshType> getSeshtypes() {

        return seshTypeRepository.findAll();
    }

    @Override
    public SeshType getSeshTypeByName(String seshTypeName) {

        Optional<SeshType> seshTypeOptional = seshTypeRepository.findByName(seshTypeName);
        if (seshTypeOptional.isEmpty()){

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return seshTypeOptional.get();
    }
}
