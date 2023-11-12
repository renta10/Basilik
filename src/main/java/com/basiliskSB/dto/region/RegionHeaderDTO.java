package com.basiliskSB.dto.region;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Satu data region yang muncul di halaman detail.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegionHeaderDTO {

	@Schema(description = "Region PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama kota region.")
	@Getter @Setter private String city;

	@Schema(description = "Keterangan dari region.")
	@Getter @Setter private String remark;
}
