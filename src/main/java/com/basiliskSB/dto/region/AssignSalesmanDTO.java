package com.basiliskSB.dto.region;
import javax.validation.constraints.NotBlank;
import com.basiliskSB.validation.UniqueAssignRegionSalesman;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data yang digunakan untuk membuat relasi antara Region dengan Salesman.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueAssignRegionSalesman(message="This salesman already work in this region.", 
	salesmanEmployeeNumberField = "salesmanEmployeeNumber", regionIdField ="regionId")
public class AssignSalesmanDTO {

	@Schema(description = "Region FK.")
	@Getter @Setter private Long regionId;

	@Schema(description = "Salesman FK.")
	@NotBlank(message="Salesman is required")
	@Getter @Setter private String salesmanEmployeeNumber;

	public AssignSalesmanDTO(String salesmanEmployeeNumber) {
		this.salesmanEmployeeNumber = salesmanEmployeeNumber;
	}
}
