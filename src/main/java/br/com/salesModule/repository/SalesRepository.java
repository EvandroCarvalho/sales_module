package br.com.salesModule.repository;

import br.com.salesModule.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    @Query(nativeQuery = true, value = "select sum(price) from sold_items;")
    public BigDecimal sumAllPrice();

    public List<Sales> findByInvoice(Long invoice);
}
