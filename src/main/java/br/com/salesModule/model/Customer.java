package br.com.salesModule.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Customer implements AbstractEntity {

	private static final long serialVersionUID = -1454337162662690055L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	private String cpf;
	private String phone;
	private String address;
	private String email;

}
