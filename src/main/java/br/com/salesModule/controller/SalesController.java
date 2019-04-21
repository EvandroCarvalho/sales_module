package br.com.salesModule.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.salesModule.model.Sales;
import br.com.salesModule.model.SalesRequest;
import br.com.salesModule.repository.SalesRepository;
import br.com.salesModule.service.GeneratedReport;
import br.com.salesModule.service.SaveListOfItemsSales;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;

@Slf4j
@RestController
@RequestMapping("v1/sales")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Endpoints to manage sales module")
public class SalesController {

	@Autowired
	private final SalesRepository salesRepository;

	@Autowired
	private SaveListOfItemsSales saveListOfItemsSales;

	@Autowired
	private GeneratedReport generatedReport;

	@GetMapping
	@ApiOperation(value = "List all available sales, paged and/or ordered", response = Sales[].class)
	public ResponseEntity<Page<Sales>> findAll(Pageable page) throws ItemsNotFound {
		Page<Sales> salesList = this.salesRepository.findAll(page);
		verifyDatabaseIsEmpty(salesList);
		return ResponseEntity.ok(salesList);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Save sale item", response = Sales.class)
	public ResponseEntity<Sales> saveItem(@Valid @RequestBody Sales item) throws URISyntaxException {
		Sales salesItem = this.salesRepository.save(item);
		return ResponseEntity.created(new URI("/v1/sales/" + salesItem.getId())).body(salesItem);
	}

	@PostMapping(path = "/saveList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Save list of sales item", response = Sales[].class)
	public ResponseEntity<?> saveListItems(@Valid @RequestBody SalesRequest listItemsSales) throws SQLDataException{
		log.info("Request save items: " + listItemsSales.toString());
		List<Sales> salesList =  this.saveListOfItemsSales.saveAllList(listItemsSales);
		return ResponseEntity.status(HttpStatus.CREATED).body(salesList);
	}

	@PutMapping
	@ApiOperation(value = "Update values ​​of an attribute", response = Sales.class)
	public ResponseEntity<Sales> update(@Valid @RequestBody Sales item) throws ItemsNotFound {
		Sales response = this.salesRepository.save(item);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Remove item available in database")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws NotFoundException {
		try {
			this.salesRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(InternalError e) {
			throw new NotFoundException("Item not found id: " + id);
		}
	}
	
	@GetMapping(path = "/report")
	@ApiOperation(value = "Returns a sales report sorted by invoice", response = JasperExportManager.class)
	public void report(HttpServletResponse response) throws JRException, SQLException, IOException {
		this.generatedReport.report(response);
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Find one item by id", response = Sales.class)
	public ResponseEntity<Sales> findById(@RequestBody @PathVariable(value = "id") Long id) throws NotFoundException {
		Sales item = this.salesRepository.findById(id).orElseThrow(() -> new ItemsNotFound("Not found by id: " + id));
		return ResponseEntity.ok().body(item);
	}
	
	@GetMapping(path = "/invoice/{invoiceId}")
	@ApiOperation(value = "Find invoice by id")
	public ResponseEntity<List<Sales>> findByInvoice(@PathVariable(value = "invoiceId")  Long invoiceId) throws ItemsNotFound {
		List<Sales> invoices = this.salesRepository.findByInvoice(invoiceId);
		if(invoices.isEmpty()) {
			throw new ItemsNotFound("Invoice not Found id: " + invoiceId);
		}
		return ResponseEntity.ok().body(invoices);
	}

	private void verifyDatabaseIsEmpty(Page<Sales> salesList) throws ItemsNotFound {
		if (salesList.isEmpty()) {
			throw new ItemsNotFound("Items not found");
		}
	}

	private void verifyHasItem(long id) throws ItemsNotFound {
		if (!this.salesRepository.existsById(id)) {
			throw new ItemsNotFound("Item not found by id: " + id);
		}
	}
}
