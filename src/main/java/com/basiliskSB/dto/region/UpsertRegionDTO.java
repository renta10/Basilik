package com.basiliskSB.dto.region;
import javax.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data region di dalam form yang digunakan untuk kebutuhan insert dan update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertRegionDTO {

	@Schema(description = "Region PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama kota dari region.")
	@NotBlank(message="City is required")
	@Size(max=50, message="City can't be more than 50 characters.")
	@Getter @Setter private String city;

	@Schema(description = "Keterangan dari region.")
	@Size(max=2000, message="Remark description can't be more than 2000 characters.")
	@Getter @Setter private String remark;
}
