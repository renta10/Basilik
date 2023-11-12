package com.basiliskSB.entity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Orders")
public class Order {
	@Id
	@Column(name="InvoiceNumber")
	@Getter @Setter private String invoiceNumber;
	
	@Column(name="CustomerId")
	@Getter @Setter private Long customerId;
	
	@ManyToOne
	@JoinColumn(name="CustomerId", insertable=false, updatable=false)
	@Getter @Setter private Customer customer;
	
	@Column(name="SalesEmployeeNumber")
	@Getter @Setter private String salesEmployeeNumber;
	
	@ManyToOne
	@JoinColumn(name="SalesEmployeeNumber", insertable=false, updatable=false)
	@Getter @Setter private Salesman salesman;
	
	@Column(name="OrderDate")
	@Getter @Setter private LocalDate orderDate;
	
	@Column(name="ShippedDate")
	@Getter @Setter private LocalDate shippedDate;
	
	@Column(name="DueDate")
	@Getter @Setter private LocalDate dueDate;
	
	@Column(name="DeliveryId")
	@Getter @Setter private Long deliveryId;
	
	@ManyToOne
	@JoinColumn(name="DeliveryId", insertable=false, updatable=false)
	@Getter @Setter private Delivery delivery;
	
	@Column(name="DeliveryCost")
	@Getter @Setter private Double deliveryCost;
	
	@Column(name="DestinationAddress")
	@Getter @Setter private String destinationAddress;
	
	@Column(name="DestinationCity")
	@Getter @Setter private String destinationCity;
	
	@Column(name="DestinationPostalCode")
	@Getter @Setter private String destinationPostalCode;
	
	@OneToMany(mappedBy="order", cascade={CascadeType.REMOVE})
	@Getter @Setter private List<OrderDetail> orderDetails;

	public Order(String invoiceNumber, Long customerId, String salesEmployeeNumber, LocalDate orderDate, LocalDate shippedDate, LocalDate dueDate,
			Long deliveryId, Double deliveryCost, String destinationAddress, String destinationCity, String destinationPostalCode) {
		this.invoiceNumber = invoiceNumber;
		this.customerId = customerId;
		this.salesEmployeeNumber = salesEmployeeNumber;
		this.orderDate = orderDate;
		this.shippedDate = shippedDate;
		this.dueDate = dueDate;
		this.deliveryId = deliveryId;
		this.deliveryCost = deliveryCost;
		this.destinationAddress = destinationAddress;
		this.destinationCity = destinationCity;
		this.destinationPostalCode = destinationPostalCode;
	}
}
