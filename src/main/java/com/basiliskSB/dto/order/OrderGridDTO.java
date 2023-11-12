package com.basiliskSB.dto.order;
import java.time.LocalDate;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transaksi order yang akan ditampilkan di halaman index grid.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderGridDTO {

	@Schema(description = "Nomor invoice PK.")
	@Getter @Setter private String invoiceNumber;

	@Schema(description = "Nama perusahaan customer.")
	@Getter @Setter private String customer;

	@Schema(description = "Nama lengkap salesman.")
	@Getter @Setter private String salesman;

	@Schema(description = "Tanggal pemesanan order.")
	@Getter @Setter private LocalDate orderDate;

	@Schema(description = "Nama perusahaan pengiriman")
	@Getter @Setter private String delivery;
}
