package com.basiliskSB.dto.account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Object JSON yang digunakan untuk me-respond permintaan JWT.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseTokenDTO {

    @Schema(description = "Username yang digunakan untuk aplikasi client-side.")
    @Getter @Setter private String username;

    @Schema(description = "Role yang digunakan untuk aplikasi client-side.")
    @Getter @Setter private String role;

    @Schema(description = "Token JWT untuk requester.")
    @Getter @Setter private String token;
}
