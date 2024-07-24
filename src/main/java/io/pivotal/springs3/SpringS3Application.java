package io.pivotal.springs3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringS3Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringS3Application.class, args);
	}
}
