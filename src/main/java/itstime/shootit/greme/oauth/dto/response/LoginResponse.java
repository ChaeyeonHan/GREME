package itstime.shootit.greme.oauth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginResponse {
    @Schema(description = "기존 회원인지 판별 => true(기존회원) / false(최초 로그인)")
    private Boolean isExistingUser;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "액세스 토큰 => 기존 회원일 때 토큰 발급")
    private String accessToken;

    //추후 홈 피드에 관련한 데이터 추가해야 함
}