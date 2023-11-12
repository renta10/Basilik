package com.basiliskSB.dto.delivery;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Delivery yang akan ditunjukan di index grid.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DeliveryGridDTO {

	@Schema(description = "Delivery PK")
	@Getter @Setter private Long id;

	@Schema(description = "Nama perusahaan pengiriman.")
	@Getter @Setter private String companyName;

	@Schema(description = "Nomor telpon perusahaan pengiriman.")
	@Getter @Setter private String phone;

	@Schema(description = "Ongkos kirim perusahaan.")
	@Getter @Setter private Double cost;
}
