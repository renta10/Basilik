package com.basiliskSB.entity;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Customers")
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="CompanyName")
	@Getter @Setter private String companyName;
	
	@Column(name="ContactPerson")	
	@Getter @Setter private String contactPerson;
	
	@Column(name="Address")
	@Getter @Setter private String address;
	
	@Column(name="City")
	@Getter @Setter private String city;
	
	@Column(name="Phone")
	@Getter @Setter private String phone;
	
	@Column(name="Email")
	@Getter @Setter private String email;
	
	@Column(name="DeleteDate")
	@Getter @Setter private LocalDateTime deleteDate;

	public Customer(Long id, String companyName, String contactPerson, String address, String city, String phone, String email) {
		this.id = id;
		this.companyName = companyName;
		this.contactPerson = contactPerson;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.email = email;
	}
}
