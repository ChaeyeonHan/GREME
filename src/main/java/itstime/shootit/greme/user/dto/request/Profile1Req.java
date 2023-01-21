package itstime.shootit.greme.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile1Req {
    @Schema(description = "닉네임")
    private String username;

    @Schema(description = "관심사 타입 => 0(에너지), 1(업사이클링), 2(친환경제품), 3(채식), 4(화장품)")
    private List<Integer> interestType;

    @Schema(description = "성별 => 0(male), 1(female), 2(Anything)")
    private Integer genderType;
}
