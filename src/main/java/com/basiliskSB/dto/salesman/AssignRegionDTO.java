package com.basiliskSB.dto.salesman;
import javax.validation.constraints.NotNull;
import com.basiliskSB.validation.UniqueAssignRegionSalesman;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data di dalam form yang digunakan untuk membuat relation antara salesman dan region.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueAssignRegionSalesman(message="This salesman already work in this region.", 
	salesmanEmployeeNumberField = "salesmanEmployeeNumber", regionIdField ="regionId")
public class AssignRegionDTO {

	@Schema(description = "Salesman Employee Number FK.")
	@Getter @Setter private String salesmanEmployeeNumber;

	@Schema(description = "Region ID FK.")
	@NotNull(message="Region is required")
	@Getter @Setter private Long regionId;
}