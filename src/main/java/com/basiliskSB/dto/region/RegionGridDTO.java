package com.basiliskSB.dto.region;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data region yang digunakan untuk ")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegionGridDTO {

	@Schema(description = "Region PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama kota dari region.")
	@Getter @Setter private String city;

	@Schema(description = "Keterangan dari region ini.")
	@Getter @Setter private String remark;
}
