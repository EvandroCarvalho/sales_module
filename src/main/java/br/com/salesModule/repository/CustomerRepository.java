package br.com.salesModule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.salesModule.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	 Optional<Customer> findByCpf(String cpf);
}
