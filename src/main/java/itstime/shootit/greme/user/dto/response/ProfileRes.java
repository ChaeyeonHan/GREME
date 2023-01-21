package itstime.shootit.greme.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRes {
    @Schema(description = "닉네임")
    private String username;

    @Schema(description = "이미지 경로")
    private String imageUrl;

    @Schema(description = "관심사 타입 => 0(에너지), 1(업사이클링), 2(친환경제품), 3(채식), 4(화장품)")
    private List<Integer> interestType;

    @Schema(description = "성별 => 0(male), 1(female), 2(Anything)")
    private Integer genderType;

    @Schema(description = "사는 지역")
    private String area;

    @Schema(description = "활동 목적")
    private String purpose;
}
