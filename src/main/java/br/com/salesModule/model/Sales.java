package br.com.salesModule.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "sold_items")
public class Sales extends AbstractEntity {

    private static final long serialVersionUID = -3899995409834599670L;


    @Builder
    public Sales(Long id, @NotNull Long itemId, @NotNull @NotEmpty String itemName, @NotNull BigDecimal sellPrice,
                 @NotNull Long invoice, Customer customer, Employee employee, User userLogged, Date createAt, Date updateAt) {
        super(createAt, updateAt);
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellPrice = sellPrice;
        this.invoice = invoice;
        this.customer = customer;
        this.employee = employee;
        this.userLogged = userLogged;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "item_id")
    @NotNull
    private Long itemId;

    @Column(name = "item_name")
    @NotNull
    @NotEmpty
    private String itemName;

    @Column(name = "sell_price")
    @NotNull
    private BigDecimal sellPrice;

    @Column(name = "Price")
    @NotNull
    private BigDecimal price;

    @Column(name = "invoice")
    @NotNull
    private Long invoice;

    @Column(name = "brand")
    private String brand;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    private Customer customer;

    @JoinColumn(name = "employee_id")
    @ManyToOne
    private Employee employee;

    @JoinColumn(name = "userLogged")
    @ManyToOne
    private User userLogged;

}
