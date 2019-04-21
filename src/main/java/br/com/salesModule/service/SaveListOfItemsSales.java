package br.com.salesModule.service;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Sales> saveAllList(SalesRequest listSales) throws SQLDataException {
		List<Sales> responseList = new ArrayList<>();
		try {
			log.info("Save list in dataBase: " + listSales.toString());
			for (Sales sales : listSales.getSalesList()) {
				responseList.add(this.salesRepository.save(sales));
			}
			return responseList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLDataException();
		}
	}
}
