package br.com.salesModule;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.salesModule.model.Sales;
import br.com.salesModule.repository.SalesRepository;
import br.com.salesModule.service.GeneratedReport;
import br.com.salesModule.service.SaveListOfItemsSales;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SalesControllerTest {
	
	@MockBean
	private SalesRepository salesRepository;
	
	@MockBean
	private SaveListOfItemsSales saveListOfItemsSales;
	
	@MockBean
	private GeneratedReport generatedReport;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static Sales sales;
	
	@BeforeClass
	public static void createSales() {
		sales = new Sales().builder()
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
	
	

}
