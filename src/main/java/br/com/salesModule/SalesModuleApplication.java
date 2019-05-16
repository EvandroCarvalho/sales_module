package br.com.salesModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalesModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesModuleApplication.class, args);
	}

}
