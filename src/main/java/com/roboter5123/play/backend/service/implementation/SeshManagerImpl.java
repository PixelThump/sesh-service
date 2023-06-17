package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.sesh.api.Sesh;
import com.roboter5123.play.backend.sesh.api.SeshFactory;
import com.roboter5123.play.backend.sesh.api.SeshType;
import com.roboter5123.play.backend.service.api.SeshManager;
import com.roboter5123.play.backend.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.service.exception.TooManySeshsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SeshManagerImpl implements SeshManager {

    private final Map<String, Sesh> seshs;
    private final Random random;
    private final SeshFactory seshfactory;
    private final Logger logger;

    @Autowired
    public SeshManagerImpl(final SeshFactory seshfactory, final Random random) {

        this.seshs = new HashMap<>();
        this.seshfactory = seshfactory;
        this.random = random;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Sesh getSesh(final String seshCode) throws NoSuchSeshException {

        final Sesh sesh = this.seshs.get(seshCode);

        if (sesh == null) {

            String errorMessage = "Could not join session with code " + seshCode + ".Session not found.";
            logger.error(errorMessage);
            throw new NoSuchSeshException(errorMessage);
        }

        return sesh;
    }

    @Override
    public Sesh createSesh(final SeshType seshType) throws TooManySeshsException {

        final Sesh sesh = seshfactory.createSesh(seshType);

        String seshCode = createSeshCode();
        sesh.setSeshCode(seshCode);

        this.seshs.put(seshCode, sesh);
        return sesh;
    }

    private String createSeshCode() throws TooManySeshsException {

        final int LETTER_A_NUMBER = 65;
        final int LETTER_Z_NUMBER = 90;
        final int codeLength = 4;

        if (seshs.size() >= (Math.pow(LETTER_Z_NUMBER - (double) LETTER_A_NUMBER, codeLength))) {

            String errorMessage = "Unable to create sesh because there were too many seshs";
            logger.error(errorMessage);
            throw new TooManySeshsException(errorMessage);
        }

        String seshCode;

        do {

            seshCode = random.ints(LETTER_A_NUMBER, LETTER_Z_NUMBER + 1).limit(codeLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        } while (this.seshs.containsKey(seshCode));

        return seshCode;
    }
}
