package br.com.salesModule.controller;

import java.util.List;

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
import br.com.salesModule.model.Employee;
import br.com.salesModule.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@Api(value = "Endpoints to manage employee")
@RequestMapping(path = "v1/employees")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	@GetMapping(path = "/findByName/{name}")
	@ApiOperation(value = "find employee by name", response = Employee[].class)
	public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable(value = "name") String name) {
		List<Employee> e = employeeRepository.findByNameContainingIgnoreCase(name);
		return ResponseEntity.ok(e);
	}

	@PostMapping
	@ApiOperation(value = "save employee", response = Employee.class)
	public ResponseEntity<Employee> save(@RequestBody Employee employeeRequest) {
		Employee employee = employeeRepository.save(employeeRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}

	@PutMapping
	@ApiOperation(value = "update values of an attribute", response = Employee.class)
	public ResponseEntity<Employee> update(@RequestBody Employee employeeRequest) {
		Employee employee = employeeRepository.save(employeeRequest);
		return ResponseEntity.ok(employee);
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove item available in database", response = ResponseEntity.class)
	public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
		} else {
			new ItemsNotFound("Not found by id: " + id);
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	@ApiOperation(value = "List all employees available, paged and/or ordered", response = Employee.class)
	public ResponseEntity<Page<Employee>> listAll(Pageable page) throws ItemsNotFound {
		Page<Employee> listEmployee = employeeRepository.findAll(page);
		if (!listEmployee.isEmpty()) {
			return ResponseEntity.ok(listEmployee);
		} else {
			throw new ItemsNotFound("Not found employees in database");
		}
	}

}
