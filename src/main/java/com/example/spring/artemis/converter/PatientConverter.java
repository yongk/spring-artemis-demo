package com.example.spring.artemis.converter;

import com.example.spring.artemis.model.Patient;
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
 * Patient与JMS Message的相互转换器。
 */
public class PatientConverter implements MessageConverter {
    private final Logger logger = LoggerFactory.getLogger(PatientConverter.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Patient patient = (Patient) object;
        String payload;
        try {
            payload = mapper.writeValueAsString(patient);
            logger.info("Outbound json '{}'", payload);
        } catch (JsonProcessingException e) {
            throw new MessageConversionException("Error converting form patient", e);
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

        Patient patient;
        try {
            patient = mapper.readValue(payload, Patient.class);
        } catch (Exception e) {
            throw new MessageConversionException("Error converting to patient", e);
        }

        return patient;
    }
}
