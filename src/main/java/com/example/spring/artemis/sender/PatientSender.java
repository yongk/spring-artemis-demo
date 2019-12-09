package com.example.spring.artemis.sender;

import com.example.spring.artemis.converter.PatientConverter;
import com.example.spring.artemis.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class PatientSender {
    private final Logger logger = LoggerFactory.getLogger(PatientSender.class);

    private JmsOperations jmsTemplate;

    private MessageConverter messageConverter;

    private String dest = "patient.queue";

    public PatientSender(JmsOperations jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = new PatientConverter();
    }

    public void send(Patient patient) {
        logger.info("Sending message [{}] to {}", patient, dest);
        jmsTemplate.send(
                dest,
                session -> messageConverter.toMessage(patient, session));
    }
}
