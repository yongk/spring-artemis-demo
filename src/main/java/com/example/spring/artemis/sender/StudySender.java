package com.example.spring.artemis.sender;

import com.example.spring.artemis.converter.StudyConverter;
import com.example.spring.artemis.model.Study;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class StudySender {
    private final Logger logger = LoggerFactory.getLogger(StudySender.class);

    private JmsOperations jmsTemplate;

    private MessageConverter messageConverter;

    private String dest = "study.queue";

    public StudySender(JmsOperations jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = new StudyConverter();
    }

    public void send(Study study) {
        logger.info("Sending message [{}] to {}", study, dest);
        jmsTemplate.send(
                dest,
                session -> messageConverter.toMessage(study, session));
    }
}
