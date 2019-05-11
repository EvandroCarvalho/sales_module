package br.com.salesModule.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.salesModule.error.ItemsNotFound;
import br.com.salesModule.model.Customer;
import br.com.salesModule.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@Api(value = "Endpoints to manage customer")
@RequestMapping(path = "v1/customers")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

	private final CustomerRepository customerRepository;

	@PostMapping
	@ApiOperation(value = "Save customer", response = Customer.class)
	public ResponseEntity<Customer> save(@RequestBody Customer custumerRequest) {
		Customer customer = customerRepository.save(custumerRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PutMapping
	@ApiOperation(value = "Update values of an attribute", response = Customer.class)
	public ResponseEntity<Customer> update(@RequestBody Customer customerRequest) {
		Customer customer = customerRepository.save(customerRequest);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete item available", response = ResponseEntity.class)
	public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) throws ItemsNotFound {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			throw new ItemsNotFound("Not found by id: " + id);
		}
	}

	@GetMapping
	@ApiOperation(value = "List all customer available, paged and/or ordered", response = Customer[].class)
	public ResponseEntity<Page<Customer>> listAll(@RequestBody Pageable page) throws ItemsNotFound {
		Page<Customer> listCustomer = customerRepository.findAll(page);
		if (listCustomer.isEmpty()) {
			throw new ItemsNotFound("Not found customers");
		} else {
			return ResponseEntity.ok(listCustomer);
		}
	}

	@GetMapping(path = "/{cpf}")
	@ApiOperation(value = "find customer by cpf", response = Customer.class)
	public ResponseEntity<Customer> findByCpf(@PathVariable(value = "cpf") String cpf) throws ItemsNotFound {
		Optional<Customer> customer = customerRepository.findByCpf(cpf);
		if (customer.isPresent()) {
			return ResponseEntity.ok(customer.get());
		} else {
			throw new ItemsNotFound("Not found by cpf: " + cpf);
		}

	}

}
