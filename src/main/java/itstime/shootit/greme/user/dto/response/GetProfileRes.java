package itstime.shootit.greme.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import itstime.shootit.greme.post.dto.response.GetPostRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileRes {

    @Schema(description = "프로필 유저의 이름")
    private String username;

    @Schema(description = "프로필 유저의 프로필 사진")
    private String profileImg;

    @Schema(description = "해당 유저가 이번달에 참여한 챌린지")
    List<GetChallengeSummaryRes> challengeSummary;

    @Schema(description = "해당 유저가 최근에 작성한 다이어리 6개")
    List<GetPostRes> postRes;
}
