package itstime.shootit.greme.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReq {
    @Schema(description = "성별 => 0(male), 1(female), 2(Anything)")
    private Integer genderType;

    @Schema(description = "사는 지역")
    private String area;

    @Schema(description = "앱 사용 목적")
    private String purpose;
}
