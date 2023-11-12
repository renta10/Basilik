package com.basiliskSB.dto.order;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.basiliskSB.validation.After;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data order dalam form yang akan digunakan untuk insert atau update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@After(message="You can't possibly ship the order before ordering.", 
	previousDateField="orderDate", subsequentDateField="shippedDate")
@After(message="You due date estimation is impossible, it happened before ship date.", 
	previousDateField="shippedDate", subsequentDateField="dueDate")
public class UpdateOrderDTO {

	@Schema(description = "Nomor invoice PK.")
	@Getter @Setter private String invoiceNumber;

	@Schema(description = "Customer ID FK.")
	@NotNull(message="Customer is required.")
	@Getter @Setter private Long customerId;

	@Schema(description = "Salesman Employee Number FK.")
	@NotBlank(message="Sales is required.")
	@Getter @Setter private String salesEmployeeNumber;

	@Schema(description = "Tanggal pemesanan dalam format yyyy-MM-dd.")
	@NotNull(message="Order date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter private LocalDate orderDate;

	@Schema(description = "Tanggal pengiriman dalam format yyyy-MM-dd.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter private LocalDate shippedDate;

	@Schema(description = "Tanggal sampai pengiriman dalam format yyyy-MM-dd.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter private LocalDate dueDate;

	@Schema(description = "Delivery ID FK.")
	@NotNull(message="Delivery company is required.")
	@Getter @Setter private Long deliveryId;

	@Schema(description = "Alamat tujuan pengiriman, maximum 200 characters.")
	@NotBlank(message="Destination address is required.")
	@Size(max=200, message="Destination address can't be more than 200 characters.")
	@Getter @Setter private String destinationAddress;

	@Schema(description = "Kota tujuan pengiriman, maximum 20 characters.")
	@NotBlank(message="Destination city is required.")
	@Size(max=20, message="Destination city can't be more than 20 characters.")
	@Getter @Setter private String destinationCity;

	@Schema(description = "Kode pos alamat tujuan, maximum 5 characters.")
	@NotBlank(message="Destination postal code is required.")
	@Size(max=5, message="Destination postal code can't be more than 5 characters.")
	@Getter @Setter private String destinationPostalCode;
}