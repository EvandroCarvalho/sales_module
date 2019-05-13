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
import br.com.salesModule.model.Product;
import br.com.salesModule.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/v1/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints to manage products")
public class ProductController {
	
	private final ProductRepository productRepository;
	
	@GetMapping
	@ApiOperation(value = "List all employees available, paged and/or ordered", response = Product[].class)
	public ResponseEntity<Page<Product>> findAll(Pageable page) throws ItemsNotFound {
		Page<Product> products = productRepository.findAll(page);
		if(!products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			throw new ItemsNotFound("Not found products");
		}
	}
	
	@PostMapping
	@ApiOperation(value = "Create save product in database", response = Product.class)
	public ResponseEntity<Product> save(@RequestBody Product product) {
		Product prod = productRepository.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(prod);
	}
	
	@PutMapping
	@ApiOperation(value= "Update values of an attribute", response = Product.class)
	public ResponseEntity<Product> update(@RequestBody Product product) {
		Product prod = productRepository.save(product);
		return ResponseEntity.ok(prod);		
	}
	
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Remove item available in database", response = ResponseEntity.class)
	public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) throws ItemsNotFound {
		if(productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			throw new ItemsNotFound("Not found by id: " + id);
		}
	}
	
	@GetMapping(path = "/findByName/{name}")
	@ApiOperation(value = "Find product by name", response = Product.class)
	public ResponseEntity<List<Product>> findByName(@PathVariable(value = "name") String name) throws ItemsNotFound {
		List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
		if(!products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			throw new ItemsNotFound("Not found by name: " + name);
		}
	}

}
