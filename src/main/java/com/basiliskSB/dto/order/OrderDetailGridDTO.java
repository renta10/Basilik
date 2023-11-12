package com.basiliskSB.dto.order;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ditunjukan di order detail grid di halaman detail.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderDetailGridDTO {

	@Schema(description = "Order Detail PK")
	@Getter @Setter private Long id;

	@Schema(description = "Nama product yang dibeli di invoice.")
	@Getter @Setter private String product;

	@Schema(description = "Harga product saat transaksi.")
	@Getter @Setter private Double price;

	@Schema(description = "Kuantitas product yang dibeli.")
	@Getter @Setter private Integer quantity;

	@Schema(description = "Persentase diskon untuk product ini.")
	@Getter @Setter private Double discount;

	@Schema(description = "Harga total dari hasil perhitungan kuantitas, harga dan diskon.")
	@Getter @Setter private Double totalPrice;

	public OrderDetailGridDTO(Long id, String product, Double price, Integer quantity, Double discount) {
		this.id = id;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
	}
}
