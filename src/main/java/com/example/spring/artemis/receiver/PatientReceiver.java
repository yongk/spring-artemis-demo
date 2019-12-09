package com.example.spring.artemis.receiver;

import com.example.spring.artemis.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PatientReceiver {
    private final Logger logger = LoggerFactory.getLogger(PatientReceiver.class);

    private final String dest = "patient.queue";

    @JmsListener(destination = dest, containerFactory = "patientContainerFactory")
    public void onMessage(Patient patient) {
        logger.info("Received message [{}] from {}", patient, dest);
    }
}
