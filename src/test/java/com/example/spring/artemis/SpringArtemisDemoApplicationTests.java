package com.example.spring.artemis;

import com.example.spring.artemis.model.Patient;
import com.example.spring.artemis.model.Study;
import com.example.spring.artemis.sender.PatientSender;
import com.example.spring.artemis.sender.StudySender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringArtemisDemoApplicationTests {
	@Autowired
	ApplicationContext context;

	@Autowired
	PatientSender patientSender;
	@Autowired
	StudySender studySender;

	@Test
	public void assertAcquireConnectionSuccess() throws JMSException {
		String[] beanNames = context.getBeanNamesForType(ConnectionFactory.class);
		assertThat(beanNames).hasSize(1);

		ConnectionFactory connectionFactory = (ConnectionFactory)context.getBean(beanNames[0]);
		Connection connection = connectionFactory.createConnection();
		assertThat(connection).isNotNull();
		// 释放资源
		connection.close();
	}


	@Test
	public void testMessageSendAndReceive() throws InterruptedException {
		Patient patient = new Patient(1, "Jacky", 18);
		patientSender.send(patient);

		Study study = new Study(100, patient);
		studySender.send(study);

		// 等待consumer消费到消息
		TimeUnit.MILLISECONDS.sleep(500);
	}
}
