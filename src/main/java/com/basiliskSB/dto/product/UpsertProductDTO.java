package com.basiliskSB.dto.product;
import javax.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data product di dalam form yang digunakan untuk insert dan update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertProductDTO {

	@Schema(description = "Product PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Nama product, maximum 50 characters.")
	@NotBlank(message="Product name is required.")
	@Size(max=200, message="Product name can't be no more than 50 characters.")
	@Getter @Setter private String name;

	@Schema(description = "Supplier ID FK")
	@Getter @Setter private Long supplierId;

	@Schema(description = "Category ID FK")
	@NotNull(message="Category is required.")
	@Getter @Setter private Long categoryId;

	@Schema(description = "Deskripsi product, maximum 1000 characters.")
	@Size(max=1000, message="Description must can't be no more than 1000 characters.")
	@Getter @Setter private String description;

	@Schema(description = "Harga product ditulis dalam angka desimal 2.")
	@NotNull(message="Unit price is required.")
	@Digits(integer=12, fraction=2, message="Must be a decimal value with 2 decimal points.")
	@Getter @Setter private Double price;

	@Schema(description = "Jumlah stock dalam bilangan bulat, maximum 9999.")
	@NotNull(message="Stock is required.")
	@Min(value=0, message="Stock cannot filled with negative value.")
	@Max(value=9999, message="Stock cannot filled with value of more than 9999.")
	@Getter @Setter private Integer stock;

	@Schema(description = "Jumlah stock yang akan di order lagi dari supplier, maximum 9999.")
	@NotNull(message="Incoming order is required.")
	@Min(value=0, message="Order cannot filled with negative value.")
	@Max(value=9999, message="Order cannot filled with value of more than 9999.")
	@Getter @Setter private Integer onOrder;

	@Schema(description = "Apakah product ini masih di supply oleh perusahaan supplier.")
	@NotNull(message="Discontinue is required.")
	@Getter @Setter private Boolean discontinue;
}
