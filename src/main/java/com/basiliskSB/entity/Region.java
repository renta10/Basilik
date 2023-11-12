package com.basiliskSB.entity;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Regions")
public class Region {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="City")
	@Getter @Setter private String city;
	
	@Column(name="Remark")
	@Getter @Setter private String remark;
	
	@ManyToMany
	@JoinTable(name="SalesmenRegions", 
		joinColumns=@JoinColumn(name="RegionId"),
		inverseJoinColumns=@JoinColumn(name="SalesmanEmployeeNumber"))
	@Getter @Setter private List<Salesman> salesmen;

	public Region(Long id, String city, String remark) {
		this.id = id;
		this.city = city;
		this.remark = remark;
	}
}
