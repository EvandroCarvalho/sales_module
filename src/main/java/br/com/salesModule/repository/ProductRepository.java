package br.com.salesModule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.salesModule.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByNameContainingIgnoreCase(String name);
}
