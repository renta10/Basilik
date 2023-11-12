package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="Name")
	@Getter @Setter private String name;
	
	@Column(name="SupplierId")
	@Getter @Setter private Long supplierId;
	
	@ManyToOne
	@JoinColumn(name="SupplierId", insertable=false, updatable=false)
	@Getter @Setter private Supplier supplier;
	
	@Column(name="CategoryId")
	@Getter @Setter private Long categoryId;
	
	@ManyToOne
	@JoinColumn(name="CategoryId", insertable=false, updatable=false)
	@Getter @Setter private Category category;
	
	@Column(name="Description")
	@Getter @Setter private String description;
	
	@Column(name="Price")
	@Getter @Setter private Double price;
	
	@Column(name="Stock")
	@Getter @Setter private Integer stock;
	
	@Column(name="OnOrder")
	@Getter @Setter private Integer onOrder;
	
	@Column(name="Discontinue")
	@Getter @Setter private Boolean discontinue;

	public Product(Long id, String name, Long supplierId, Long categoryId, String description, Double price,
			Integer stock, Integer onOrder, Boolean discontinue) {
		this.id = id;
		this.name = name;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.onOrder = onOrder;
		this.discontinue = discontinue;
	}
}
