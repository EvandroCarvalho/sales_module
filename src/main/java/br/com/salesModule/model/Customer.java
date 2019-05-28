package br.com.salesModule.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractEntity {

    private static final long serialVersionUID = -5546297725248514790L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "date_of_birth")
    private Date date_of_birth;
    @Column(name = "salary")
    private Long salary;

}
