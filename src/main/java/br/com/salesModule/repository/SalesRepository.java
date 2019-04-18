package br.com.salesModule.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.salesModule.model.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
			@Query(nativeQuery = true, value ="select sum(price) from sold_items;" )
			public BigDecimal sumAllPrice();
			
}
