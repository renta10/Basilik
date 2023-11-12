package com.basiliskSB.dto.account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data object yang digunakan untuk me-request JWT.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestTokenDTO {

    @Schema(description = "Username maximum 20 characters.")
    @Getter @Setter private String username;

    @Schema(description = "Password maximum 20 characters.")
    @Getter @Setter private String password;

    @Schema(description = "Username, Email atau topic dari requester.")
    @Getter @Setter private String subject;

    @Schema(description = "Secret key dari API")
    @Getter @Setter private String secretKey;

    @Schema(description = "Pengguna API")
    @Getter @Setter private String audience;
}
