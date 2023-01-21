package itstime.shootit.greme.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile2Req {
    @Schema(description = "사는 지역")
    private String area;

    @Schema(description = "앱 사용 목적")
    private String purpose;
}
