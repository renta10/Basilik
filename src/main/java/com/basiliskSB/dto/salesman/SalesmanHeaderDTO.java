package com.basiliskSB.dto.salesman;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Satu data salesman yang akan ditampilkan di halaman detail.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SalesmanHeaderDTO {

	@Schema(description = "Employee number.")
	@Getter @Setter private String employeeNumber;

	@Schema(description = "Nama lengkap karyawan.")
	@Getter @Setter private String fullName;
}
