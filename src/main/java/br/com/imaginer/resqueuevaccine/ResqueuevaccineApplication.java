package br.com.imaginer.resqueuevaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ResqueuevaccineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResqueuevaccineApplication.class, args);
	}

}
