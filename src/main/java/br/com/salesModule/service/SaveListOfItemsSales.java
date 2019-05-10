package br.com.salesModule.service;

import java.sql.SQLDataException;
import java.util.List;

import br.com.salesModule.model.Sales;
import br.com.salesModule.model.SalesRequest;

public interface SaveListOfItemsSales {
	public List<Sales> saveAllList(SalesRequest listSales) throws SQLDataException;
	
}
