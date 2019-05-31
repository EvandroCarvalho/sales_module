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
@Builder
@AllArgsConstructor
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
}
