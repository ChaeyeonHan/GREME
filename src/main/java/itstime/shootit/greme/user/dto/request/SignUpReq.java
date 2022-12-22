package itstime.shootit.greme.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
    @Schema(description = "이메일")
    private String email;

    @Schema(description = "닉네임")
    private String username;
}