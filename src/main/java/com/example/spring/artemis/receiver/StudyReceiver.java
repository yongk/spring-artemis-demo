package com.example.spring.artemis.receiver;

import com.example.spring.artemis.model.Study;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class StudyReceiver {
    private final Logger logger = LoggerFactory.getLogger(StudyReceiver.class);

    private final String dest = "study.queue";

    @JmsListener(destination = dest, containerFactory = "studyContainerFactory")
    public void onMessage(Study study) {
        logger.info("Received message [{}] from {}", study, dest);
    }
}
