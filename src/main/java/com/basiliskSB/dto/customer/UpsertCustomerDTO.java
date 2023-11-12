package com.basiliskSB.dto.customer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data form yang digunakan untuk menambah atau merubah data Customer")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertCustomerDTO {

	@Schema(description = "Customer PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama perusahaan Customer, maximum 50 characters.")
	@NotBlank(message="Company name is required.")
	@Size(max=50, message="Company name can't be no more than 50 characters.")
	@Getter @Setter private String companyName;

	@Schema(description = "Nama lengkap perwakilan Customer, maximum 200 characters.")
	@NotBlank(message="Contact person name is required.")
	@Size(max=200, message="Contact person can't be no more than 200 characters.")
	@Getter @Setter private String contactPerson;

	@Schema(description = "Alamat dari perusahaan Customer, maximum 500 characters.")
	@Size(max=500, message="Address can't be no more than 500 characters.")
	@Getter @Setter private String address;

	@Schema(description = "Lokasi kota dari perusahaan Customer")
	@Size(max=100, message="City can't be no more than 100 characters.")
	@Getter @Setter private String city;

	@Schema(description = "Nomor mobil dari perwakilan.")
	@Size(max=20, message="Phone can't be no more than 20 characters.")
	@Getter @Setter private String phone;

	@Schema(description = "Email dari perusahaan atau perwakilan dari Customer.")
	@Size(max=50, message="Email can't be no more than 50 characters.")
	@Getter @Setter private String email;
}
