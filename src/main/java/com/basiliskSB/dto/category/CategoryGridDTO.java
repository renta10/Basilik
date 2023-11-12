package com.basiliskSB.dto.category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data Category yang untuk ditampilkan di table.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryGridDTO{

	@Schema(description = "PK Category.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama category product.")
	@Getter @Setter private String name;

	@Schema(description = "Deskripsi dari category.")
	@Getter @Setter private String description;
}