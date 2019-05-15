package br.com.salesModule.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sold_items")
public class Sales implements AbstractEntity{
	
	private static final long serialVersionUID = -3899995409834599670L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "item_id")
	@NotNull
	private Long itemId;
	
	@Column(name = "item_description")
	@NotNull
	@NotEmpty
	private String itemDescipton;
	
	@Column(name = "price")
	@NotNull
	private BigDecimal price;
	
	@Column(name ="invoice")
	@NotNull
	private Long invoice;
	
	@Column(name = "customer")
	@NotNull
	private Customer customer;
	
	@Column(name = "employee")
	@NotNull
	private Employee employee;
	
	@Column(name = "sales_time")
	private Date salesTime = Calendar.getInstance().getTime();

}
