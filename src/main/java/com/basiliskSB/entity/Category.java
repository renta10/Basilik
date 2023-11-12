package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Categories")
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	@Getter @Setter private Long id;
	
	@Column(name="Name")
	@Getter @Setter private String name;
	
	@Column(name="Description")
	@Getter @Setter private String description;
}
