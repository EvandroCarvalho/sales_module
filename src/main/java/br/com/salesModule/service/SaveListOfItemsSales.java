package br.com.salesModule.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.salesModule.model.Sales;
import br.com.salesModule.model.SalesRequest;
import br.com.salesModule.repository.SalesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SaveListOfItemsSales {

	@Autowired
	private final SalesRepository salesRepository;

	@Transactional
	@Valid
	public ResponseEntity<?> saveAllList(SalesRequest listSales) {
		List<Sales> response = new ArrayList<>();
		try {
			log.info("Save list in dataBase: " + listSales.toString());
			for (Sales sales : listSales.getSalesList()) {
				response.add(this.salesRepository.save(sales));
			}
			return ResponseEntity.created(new URI("v1/sales")).body(response);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
	}
}
