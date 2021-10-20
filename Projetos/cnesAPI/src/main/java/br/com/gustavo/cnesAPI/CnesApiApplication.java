package br.com.gustavo.cnesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class CnesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnesApiApplication.class, args);
	}

}
