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
import br.com.salesModule.model.User;
import br.com.salesModule.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints to manage users")
@Slf4j
public class UserController {

	@Autowired
	private final UserRepository userRepository;

	@PostMapping
	@ApiOperation(value = "Save user", response = User.class)
	public ResponseEntity<User> save(@RequestBody User userRequest) {
		log.info("Request: " + userRequest.toString());
		User user = userRepository.save(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PutMapping
	@ApiOperation(value = "Update values of an attribute", response = User.class)
	public ResponseEntity<User> update(@RequestBody User userRequest) {
		User user = userRepository.save(userRequest);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete item available", response = ResponseEntity.class)
	public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) throws ItemsNotFound {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			throw new ItemsNotFound("Not found by id: " + id);
		}
	}

	@GetMapping
	@ApiOperation(value = "List all customer available, paged and/or ordered", response = User[].class)
	public ResponseEntity<Page<User>> listAll(Pageable page) throws ItemsNotFound {
		Page<User> listCustomer = userRepository.findAll(page);
		if (listCustomer.isEmpty()) {
			throw new ItemsNotFound("Not found customers");
		} else {
			return ResponseEntity.ok(listCustomer);
		}
	}

	@GetMapping(path = "/findByName/{cpf}")
	@ApiOperation(value = "find user by cpf", response = Customer.class)
	public ResponseEntity<User> findByCpf(@PathVariable(value = "cpf") String cpf) throws ItemsNotFound {
		Optional<User> user = userRepository.findByCpf(cpf);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			throw new ItemsNotFound("Not found by cpf: " + cpf);
		}
	}
	

}
