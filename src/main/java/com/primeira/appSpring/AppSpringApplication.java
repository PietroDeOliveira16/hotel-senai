package com.primeira.appSpring;

import com.primeira.appSpring.program.ArduinoSerialCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppSpringApplication implements CommandLineRunner {
	@Autowired
	private ArduinoSerialCommunication arduinoSerialCommunication;

	public static void main(String[] args) {
		SpringApplication.run(AppSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		arduinoSerialCommunication.initialize();
	}
}
