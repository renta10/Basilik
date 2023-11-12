package com.basiliskSB.dto.salesman;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data salesman yang akan dikeluarkan di grid di halaman index.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SalesmanGridDTO {

	@Schema(description = "Employee Number PK.")
	@Getter @Setter private String employeeNumber;

	@Schema(description = "Nama lengkap salesman.")
	@Getter @Setter private String fullName;

	@Schema(description = "Level karyawan.")
	@Getter @Setter private String level;

	@Schema(description = "Nama lengkap supervisor dari karyawan ini.")
	@Getter @Setter private String superior;
}
