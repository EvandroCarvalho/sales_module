package br.com.salesModule.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.salesModule.util.LocalDataDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "customer")
public class Customer extends AbstractEntity {

    private static final long serialVersionUID = -5546297725248514790L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @NotNull
    @NotBlank
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    @JsonDeserialize(using = LocalDataDeserializer.class)
    private LocalDate birthDate;
    @Column(name = "salary")
    private BigDecimal salary;
    @Column(name = "gender")
    private Character gender;
    
    private Customer (String name, String cpf, String phone, String address, String email, LocalDate birthDate, BigDecimal salary, Character gender) {
    	this.name = name;
    	this.cpf = cpf;
    	this.phone = phone;
    	this.email = email;
    	this.birthDate = birthDate;
    	this.salary = salary;
    	this.gender = gender;
    }
    
    public static class Builder {
        private String name;
        private String cpf;
        private String phone;
        private String address;
        private String email;
        private LocalDate birthDate;
        private BigDecimal salary;
        private Character gender;
        
    	public Builder name(String name) {
    		this.name = name;
    		return this;
    	}
    	public Builder cpf(String cpf) {
    		this.cpf = cpf;
    		return this;
    	}
    	public Builder phone(String phone) {
    		this.phone = phone;
    		return this;
    	}
    	public Builder addres(String address) {
    		this.address = address;
    		return this;
    	}
    	public Builder email(String email) {
    		this.email = email;
    		return this;
    	}
    	public Builder birthData(LocalDate birthDate) {
    		this.birthDate = birthDate;
    		return this;
    	}
    	public Builder salary(BigDecimal salary) {
    		this.salary = salary;
    		return this;
    	}
    	public Builder gender(Character gender) {
    		this.gender = gender;
    		return this;
    	}
    	public Customer build() {
    		return new Customer(name, cpf, phone, address, email, birthDate, salary, gender);
    	}
		@Override
		public String toString() {
			return "Builder [name=" + name + ", cpf=" + cpf + ", phone=" + phone + ", address=" + address + ", email="
					+ email + ", birthDate=" + birthDate + ", salary=" + salary + ", gender=" + gender + "]";
		}
    	
    	
    }
}
