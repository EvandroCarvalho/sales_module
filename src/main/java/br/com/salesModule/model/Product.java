package br.com.salesModule.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product implements AbstractEntity {

	private static final long serialVersionUID = -947277822634843981L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@Column(name = "description")
	private String description;
	@NotNull
	@Column(name = "amount")
	private int amount;
	@Column(name = "price")
	private BigDecimal price;
	
	
	
	

}
