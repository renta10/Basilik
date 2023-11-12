package com.basiliskSB.dto.order;
import java.time.LocalDate;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Satu data order header yang ditunjukan di dalam halaman detail order.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderHeaderDTO {

	@Schema(description = "Nomor invoice PK.")
	@Getter @Setter private String invoiceNumber;

	@Schema(description = "Nama perusahaan customer.")
	@Getter @Setter private String customer;

	@Schema(description = "Nama lengkap salesman.")
	@Getter @Setter private String salesman;

	@Schema(description = "Tanggal pemesanan.")
	@Getter @Setter private LocalDate orderDate;
}
