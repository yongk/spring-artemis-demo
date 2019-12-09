package com.example.spring.artemis.config;

import com.example.spring.artemis.converter.PatientConverter;
import com.example.spring.artemis.converter.StudyConverter;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
class JmsReceiverConfig {

    @Bean
    DefaultJmsListenerContainerFactory patientContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            ConnectionFactory jmsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, jmsConnectionFactory);
        factory.setMessageConverter(new PatientConverter());
        return factory;
    }

    @Bean
    DefaultJmsListenerContainerFactory studyContainerFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            ConnectionFactory jmsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, jmsConnectionFactory);
        factory.setMessageConverter(new StudyConverter());
        return factory;
    }
}
