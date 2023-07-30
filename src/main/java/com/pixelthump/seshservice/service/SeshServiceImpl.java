package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.SeshRepository;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SeshServiceImpl implements SeshService {

    private final SeshRepository seshRepository;
    private final SeshTypeService seshTypeService;
    private final SeshFactory seshFactory;
    private final Random random;

    @Autowired
    public SeshServiceImpl(final SeshRepository seshRepository, SeshTypeService seshTypeService, SeshFactory seshFactory, Random random) {

        this.seshRepository = seshRepository;
        this.seshTypeService = seshTypeService;
        this.seshFactory = seshFactory;
        this.random = random;
    }

    @Override
    public Sesh createSesh(String seshTypeName) {

        SeshType seshType = getSeshtype(seshTypeName);
        String seshCode = generateSeshCode();
        Sesh sesh = seshFactory.createSesh(seshCode, seshType);
        return seshRepository.save(sesh);
    }

    @Override
    public Sesh getSesh(String seshCode) throws ResponseStatusException {

        Optional<Sesh> sesh = seshRepository.findBySeshCode(seshCode);
        if (sesh.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return sesh.get();
    }

    private SeshType getSeshtype(String seshTypeName) {

        try {

            return seshTypeService.getSeshTypeByName(seshTypeName);
        } catch (ResponseStatusException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    private String generateSeshCode() {

        List<Sesh> seshs = seshRepository.findAll();

        final int LETTER_A_NUMBER = 65;
        final int LETTER_Z_NUMBER = 90;
        final int codeLength = 4;
        if (seshs.size() >= (Math.pow(LETTER_Z_NUMBER - (double) LETTER_A_NUMBER, codeLength))) {

            String errorMessage = "Unable to create sesh because there were too many seshs";
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }

        List<String> seshCodes = seshs.stream().map(Sesh::getSeshCode).toList();
        String seshCode;

        do {

            seshCode = random.ints(LETTER_A_NUMBER, LETTER_Z_NUMBER + 1).limit(codeLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        } while (seshCodes.contains(seshCode));

        return seshCode;
    }
}
