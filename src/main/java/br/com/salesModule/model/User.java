package br.com.salesModule.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity implements Serializable {

//    private static final long serialVersionUID = -5546297725248514790L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Column(name = "username")
    private String username;
    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotNull
    @NotBlank
    @Column(name = "profile")
    private String profile;

}
