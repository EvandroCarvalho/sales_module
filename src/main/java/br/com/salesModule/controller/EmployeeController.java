package br.com.salesModule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.salesModule.model.Employee;
import br.com.salesModule.repository.EmployeeRepository;
import lombok.AllArgsConstructor;


@RequestMapping(path = "employees")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeController {
	
	private final EmployeeRepository employeeRepository;
	
	@GetMapping(path = "/{name}")
	public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable(value = "name") String name) {
		List<Employee> e = employeeRepository.findByNameContainingIgnoreCase(name);
		return ResponseEntity.ok(e);
	}

}
