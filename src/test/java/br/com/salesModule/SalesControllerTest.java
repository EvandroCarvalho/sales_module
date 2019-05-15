package br.com.salesModule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.salesModule.model.Sales;
import br.com.salesModule.model.SalesRequest;
import br.com.salesModule.repository.CustomerRepository;
import br.com.salesModule.repository.EmployeeRepository;
import br.com.salesModule.repository.ProductRepository;
import br.com.salesModule.repository.SalesRepository;
import br.com.salesModule.service.GeneratedReportImpl;
import br.com.salesModule.service.SaveListOfItemsSalesImpl;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SalesControllerTest {
	
	
	@MockBean
	private SalesRepository salesRepository;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private SaveListOfItemsSalesImpl saveListOfItemsSales;
	
	@MockBean
	private GeneratedReportImpl generatedReport;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static Sales sales;
	
	@BeforeClass
	public static void createSales() {
		sales = new Sales().builder()
				.id((long)1)
				.itemId((long)1)
				.itemDescipton("product")
				.price(new BigDecimal(20.5))
				.invoice((long)123)
				.build();	
	}
	
	@Test
	public void findAll() throws Exception {
		List<Sales> salesList =  Arrays.asList(sales);
		Page<Sales> pageSales = new PageImpl<>(salesList);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesListPagebleJSON = ow.writeValueAsString(pageSales);
		
		when(salesRepository.findAll(any(Pageable.class))).thenReturn(pageSales);
		
		this.mockMvc.perform(get("/v1/sales")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().json(salesListPagebleJSON));
	}
	
	@Test
	public void findAllError() throws Exception {
		Page<Sales> pageSales = new PageImpl<>(Collections.EMPTY_LIST);
		
		when(salesRepository.findAll(any(Pageable.class))).thenReturn(pageSales);
		
		this.mockMvc.perform(get("/v1/sales")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void saveItem() throws Exception {
		when(salesRepository.save(any(Sales.class))).thenReturn(sales);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesJSON = ow.writeValueAsString(sales);
		
		this.mockMvc.perform(post("/v1/sales")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(salesJSON))
		.andExpect(status().isCreated())
		.andExpect(content().json(salesJSON));
	}
	
	@Test
	public void saveItemError() throws Exception {
		Sales salesError = new Sales().builder()
				.itemId(null)
				.itemDescipton("product")
				.price(new BigDecimal(20.5))
				.invoice((long)123)
				.build(); 
		
		when(salesRepository.save(any(Sales.class))).thenReturn(salesError);		
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesJSON = ow.writeValueAsString(salesError);
		
		this.mockMvc.perform(post("/v1/sales")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(salesJSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void saveListItems() throws Exception {
		SalesRequest salesRequest = new SalesRequest();
		List<Sales> salesList = Arrays.asList(sales);
		when(saveListOfItemsSales.saveAllList(any(SalesRequest.class))).thenReturn(salesList);
		salesRequest.setSalesList(salesList);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesListJSON = ow.writeValueAsString(salesList);
		String salesRequestJSON = ow.writeValueAsString(salesRequest);
		
		this.mockMvc.perform(post("/v1/sales/saveList")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(salesRequestJSON)
				)
		.andExpect(status().isCreated())
		.andExpect(content().json(salesListJSON));
	}
	
	@Test
	public void saveListItemsError() throws Exception {
		SalesRequest salesRequest = new SalesRequest();
		List<Sales> salesList = Arrays.asList(sales);
		when(saveListOfItemsSales.saveAllList(any(SalesRequest.class))).thenReturn(salesList);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesRequestJSON = ow.writeValueAsString(salesRequest);
		
		this.mockMvc.perform(post("/v1/sales/saveList")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(salesRequestJSON)
				)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void update() throws Exception {
		when(salesRepository.save(sales)).thenReturn(sales);
		sales.setItemDescipton("product 8");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesJSON = ow.writeValueAsString(sales);
		when(salesRepository.save(any(Sales.class))).thenReturn(sales);
		
		
		this.mockMvc.perform(put("/v1/sales")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(salesJSON))
		.andExpect(status().isOk())
		.andExpect(content().json(salesJSON));
	}
	
	@Test
	public void delete() throws Exception {
		when(salesRepository.save(any(Sales.class))).thenReturn(sales);
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/v1/sales/{id}", "1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}
	
	@Test
	public void report() throws Exception {
		when(salesRepository.save(any(Sales.class))).thenReturn(sales);
		this.mockMvc.perform(get("/v1/sales/report")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_PDF))
		.andExpect(status().isOk());
	}
	
	@Test
	public void findById() throws Exception {
		when(salesRepository.findById(any(Long.class))).thenReturn(Optional.of(sales));
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesJSON = ow.writeValueAsString(sales);
		this.mockMvc.perform(get("/v1/sales/{id}", "1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().json(salesJSON));
	}
	
	@Test
	public void findByIdError() throws Exception {
		this.mockMvc.perform(get("/v1/sales/{id}", "1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void findInvoiceById() throws Exception {
		List<Sales> salesList = Arrays.asList(sales);
		when(salesRepository.findByInvoice(any(Long.class))).thenReturn(salesList);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String salesListJSON = ow.writeValueAsString(salesList);
		this.mockMvc.perform(get("/v1/sales/invoice/{invoiceId}", "1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().json(salesListJSON));
	}
	
	@Test
	public void findInvoiceByIdError() throws Exception {
		this.mockMvc.perform(get("/v1/sales/invoice/{invoiceId}", "1")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

}
