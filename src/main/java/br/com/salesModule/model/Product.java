package br.com.salesModule.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product extends AbstractEntity {

	private static final long serialVersionUID = -947277822634843981L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@NotBlank
	@Column(name = "name")
	private String name;

	@Column(name = "brand")
	private String brand;

	@Column(name = "cost_price")
	private BigDecimal price;

	@Column(name = "sell_price")
	private BigDecimal sellPrice;

}
