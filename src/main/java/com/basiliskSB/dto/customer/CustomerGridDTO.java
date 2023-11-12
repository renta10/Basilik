package com.basiliskSB.dto.customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data Customer yang akan ditampilkan di index grid.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerGridDTO {

	@Schema(description = "Customer PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama perusahaan Customer.")
	@Getter @Setter private String companyName;

	@Schema(description = "Nama seseorang wakil dari perusahaan Customer.")
	@Getter @Setter private String contactPerson;

	@Schema(description = "Alamat perusahaan customer.")
	@Getter @Setter private String address;

	@Schema(description = "Lokasi kota dari perusahaan Customer.")
	@Getter @Setter private String city;
}
