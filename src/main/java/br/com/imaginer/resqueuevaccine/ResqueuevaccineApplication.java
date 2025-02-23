package br.com.imaginer.resqueuevaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResqueuevaccineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResqueuevaccineApplication.class, args);
	}

}
