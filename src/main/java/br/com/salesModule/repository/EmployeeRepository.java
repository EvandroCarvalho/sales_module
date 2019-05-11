package br.com.salesModule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import br.com.salesModule.model.Employee;

@CrossOrigin
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	List<Employee> findByNameContainingIgnoreCase(String name);
}
