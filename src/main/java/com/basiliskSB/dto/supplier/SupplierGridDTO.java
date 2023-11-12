package com.basiliskSB.dto.supplier;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Supplier yang akan ditunjukan di dalam grid di dalam halaman index.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SupplierGridDTO {

	@Schema(description = "Supplier ID PK")
	@Getter @Setter private Long id;

	@Schema(description = "Nama perusahaan Supplier.")
	@Getter @Setter private String companyName;

	@Schema(description = "Nama orang perwakilan dari Supplier.")
	@Getter @Setter private String contactPerson;

	@Schema(description = "Jabatan dari perwakilan Supplier.")
	@Getter @Setter private String jobTitle;
}
