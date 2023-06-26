package com.roboter5123.play.backend.seshservice.service.implementation;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
@Log4j2
public class SeshManagerImpl implements SeshManager {

    private final Map<String, Sesh> seshs;
    private final Random random;
    private final SeshFactory seshfactory;

    @Autowired
    public SeshManagerImpl(final SeshFactory seshfactory, final Random random) {

        this.seshs = new HashMap<>();
        this.seshfactory = seshfactory;
        this.random = random;
    }

    @Override
    public Sesh getSesh(final String seshCode) throws NoSuchSeshException {

        final Sesh sesh = this.seshs.get(seshCode);

        if (sesh == null) {

            String errorMessage = "Could not join session with code " + seshCode + ".Session not found.";
            log.error(errorMessage);
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

    @Override
    public void clearSeshs() {

        this.seshs.clear();
    }

    private String createSeshCode() throws TooManySeshsException {

        final int LETTER_A_NUMBER = 65;
        final int LETTER_Z_NUMBER = 90;
        final int codeLength = 4;

        if (seshs.size() >= (Math.pow(LETTER_Z_NUMBER - (double) LETTER_A_NUMBER, codeLength))) {

            String errorMessage = "Unable to create sesh because there were too many seshs";
            log.error(errorMessage);
            throw new TooManySeshsException(errorMessage);
        }

        String seshCode;

        do {

            seshCode = random.ints(LETTER_A_NUMBER, LETTER_Z_NUMBER + 1).limit(codeLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        } while (this.seshs.containsKey(seshCode));

        return seshCode;
    }

    @Scheduled(fixedDelay = 5L, initialDelay = 5L, timeUnit = TimeUnit.MINUTES)
    private void deleteUnusedSeshs() {

        long deletedSeshs = 0L;
        log.info("Checking for idle Seshs.");
        for (Map.Entry<String, Sesh> seshEntry : seshs.entrySet()) {

            LocalDateTime currentTimePlusMaxIdleTime = LocalDateTime.now().minusMinutes(10L);

            LocalDateTime lastInteractionTime = seshEntry.getValue().getLastInteractionTime();
            if (lastInteractionTime.isBefore(currentTimePlusMaxIdleTime)) {

                this.seshs.remove(seshEntry.getKey());
                deletedSeshs += 1;
                log.info("Deleted a Sesh; Code: {} Sesh= {}", seshEntry.getKey(), seshEntry.getValue());
            }
        }

        log.info("Finished deleting idle Seshs. Deleted {} Seshs", deletedSeshs);
    }
}
