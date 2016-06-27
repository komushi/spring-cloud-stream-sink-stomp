package io.pivotal.spring.cloud.stream.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StompSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StompSinkApplication.class, args);
	}
}
