package com.basiliskSB.dto.product;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data yang akan ditunjukan di product grid di halaman index.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductGridDTO {

	@Schema(description = "Product PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama product, nama product tidak bersifat unik.")
	@Getter @Setter private String name;

	@Schema(description = "Nama perusahaan supplier.")
	@Getter @Setter private String supplier;

	@Schema(description = "Nama dari category product")
	@Getter @Setter private String category;

	@Schema(description = "Harga product dalam rupiah.")
	@Getter @Setter private Double price;
}
