package br.com.salesModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.salesModule.model.Customer;
import br.com.salesModule.model.Employee;

@SpringBootApplication
@EnableJpaAuditing
public class SalesModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesModuleApplication.class, args);				
	}

}
