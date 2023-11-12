package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name="Accounts")
public class Account {

	@Id
	@Column(name="Username")
	@Getter @Setter private String username;
	
	@Column(name="Password")
	@Getter @Setter private String password;
	
	@Column(name="Role")
	@Getter @Setter private String role;
}
