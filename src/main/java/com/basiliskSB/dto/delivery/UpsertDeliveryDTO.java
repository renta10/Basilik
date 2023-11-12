package com.basiliskSB.dto.delivery;
import javax.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data dari form delivery yang digunakan untuk insert dan update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertDeliveryDTO {

	@Schema(description = "PK Delivery")
	@Getter @Setter private Long id;

	@Schema(description = "Nama perusahaan delivery, maximum 50 characters.")
	@NotBlank(message="Company name is required.")
	@Size(max=50, message="Company name can't be no more than 50 characters.")
	@Getter @Setter private String companyName;

	@Schema(description = "Nomor telphone dari perusahaan delivery, maximum 20 characters.")
	@Size(max=20, message="Phone can't be no more than 20 characters.")
	@Getter @Setter private String phone;

	@Schema(description = "Ongokos dalam bilangan decimal dengan 2 angka dibelakang koma.")
	@NotNull(message="Cost is required.")
	@Digits(integer=12, fraction=2, message="Must be a decimal value with 2 decimal points.")
	@Getter @Setter private Double cost;
}
