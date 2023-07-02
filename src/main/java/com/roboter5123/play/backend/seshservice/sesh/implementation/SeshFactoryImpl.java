package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SeshFactoryImpl implements SeshFactory {

    private final ApplicationContext applicationContext;

    public SeshFactoryImpl(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    @Override
    public Sesh createSesh(String seshCode, SeshType seshType) throws UnsupportedOperationException {

        final Sesh sesh;

        if (seshType == SeshType.QUIZXEL) {

            sesh = applicationContext.getBean(QuizxelSesh.class);

        } else {

            throw new UnsupportedOperationException("No sesh of seshtype " + seshType.name() + " is supported.");
        }

        sesh.setSeshCode(seshCode);
        sesh.startSesh();
        return sesh;
    }
}
