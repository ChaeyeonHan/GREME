package itstime.shootit.greme.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRes {
    @Schema(description = "닉네임")
    private String username;

    @Schema(description = "이미지 경로")
    private String imageUrl;

    @Schema(description = "참여 중인 챌린지 리스트")
    private List<GetChallengeSummaryRes> challengeJoinSummary;
}
