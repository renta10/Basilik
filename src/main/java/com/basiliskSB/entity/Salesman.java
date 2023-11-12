package com.basiliskSB.entity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Salesmen")
public class Salesman {
	@Id
	@Column(name="EmployeeNumber")
	@Getter @Setter private String employeeNumber;
	
	@Column(name="FirstName")
	@Getter @Setter private String firstName;
	
	@Column(name="LastName")
	@Getter @Setter private String lastName;
	
	@Column(name="Level")
	@Getter @Setter private String level;
	
	@Column(name="BirthDate")
	@Getter @Setter private LocalDate birthDate;
	
	@Column(name="HiredDate")
	@Getter @Setter private LocalDate hiredDate;
	
	@Column(name="Address")
	@Getter @Setter private String address;
	
	@Column(name="City")
	@Getter @Setter private String city;
	
	@Column(name="Phone")
	@Getter @Setter private String phone;
	
	@Column(name="SuperiorEmployeeNumber")
	@Getter @Setter private String superiorEmployeeNumber;
	
	@ManyToOne
	@JoinColumn(name="SuperiorEmployeeNumber", insertable=false, updatable=false)
	@Getter @Setter private Salesman superior;

	@ManyToMany
	@JoinTable(name="SalesmenRegions", 
		joinColumns=@JoinColumn(name="SalesmanEmployeeNumber"),
		inverseJoinColumns=@JoinColumn(name="RegionId"))
	@Getter @Setter private List<Region> regions;

	public Salesman(String employeeNumber, String firstName, String lastName, String level, LocalDate birthDate, LocalDate hiredDate, String address,
			String city, String phone, String superiorEmployeeNumber) {
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.level = level;
		this.birthDate = birthDate;
		this.hiredDate = hiredDate;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.superiorEmployeeNumber = superiorEmployeeNumber;
	}
}
