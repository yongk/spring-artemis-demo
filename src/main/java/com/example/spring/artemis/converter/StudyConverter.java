package com.example.spring.artemis.converter;

import com.example.spring.artemis.model.Study;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Study与JMS Message的相互转换器。
 */
public class StudyConverter implements MessageConverter {
    private final Logger logger = LoggerFactory.getLogger(StudyConverter.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Study study = (Study) object;
        String payload;
        try {
            payload = mapper.writeValueAsString(study);
            logger.info("Outbound json '{}'", payload);
        } catch (JsonProcessingException e) {
            throw new MessageConversionException("Error converting form study", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        logger.info("Inbound json '{}'", payload);

        Study study;
        try {
            study = mapper.readValue(payload, Study.class);
        } catch (Exception e) {
            throw new MessageConversionException("Error converting to study", e);
        }

        return study;
    }
}
