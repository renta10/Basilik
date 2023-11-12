package com.basiliskSB.dto.order;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data order detail di dalam form yang digunakan untuk insert dan update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertOrderDetailDTO {

	@Schema(description = "Order detail ID PK.")
	@Getter @Setter private Long id;

	@Schema(description = "Invoice Number FK.")
	@Getter @Setter private String invoiceNumber;

	@Schema(description = "Product ID FK.")
	@NotNull(message="Product is required.")
	@Getter @Setter private Long productId;

	@Schema(description = "Jumlah product yang dibeli.")
	@NotNull(message="Customer is required.")
	@Min(value=0, message="Quantity cannot filled with negative value.")
	@Max(value=9999, message="Quantity cannot filled with value of more than 9999.")
	@Getter @Setter private Integer quantity;

	@Schema(description = "Persentase dalam bilangan desimal dengan 2 angka dibelakang koma, maximum 100.00.")
	@NotNull(message="Discount is required.")
	@Digits(integer=3, fraction=2, message="Must be a decimal value with 2 decimal points.")
	@Min(value=0, message="Discount cannot filled with negative value.")
	@Max(value=100, message="Discount cannot filled with value of more than 100.")
	@Getter @Setter private Double discount;
}
