package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Deliveries")
public class Delivery {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="CompanyName")
	@Getter @Setter private String companyName;
	
	@Column(name="Phone")
	@Getter @Setter private String phone;
	
	@Column(name="Cost")
	@Getter @Setter private Double cost;
}
