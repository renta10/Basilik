package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="OrderDetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="InvoiceNumber")
	@Getter @Setter private String invoiceNumber;
	
	@ManyToOne
	@JoinColumn(name="InvoiceNumber", insertable=false, updatable=false)
	@Getter @Setter private Order order;
	
	@Column(name="ProductId")
	@Getter @Setter private Long productId;
	
	@ManyToOne
	@JoinColumn(name="ProductId", insertable=false, updatable=false)
	@Getter @Setter private Product product;
	
	@Column(name="UnitPrice")
	@Getter @Setter private Double unitPrice;
	
	@Column(name="Quantity")
	@Getter @Setter private Integer quantity;
	
	@Column(name="Discount")
	@Getter @Setter private Double discount;

	public OrderDetail(Long id, String invoiceNumber, Long productId, Double unitPrice, Integer quantity, Double discount) {
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
	}
	
	
}
