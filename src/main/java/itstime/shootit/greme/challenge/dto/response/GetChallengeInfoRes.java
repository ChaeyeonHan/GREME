package itstime.shootit.greme.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetChallengeInfoRes {
    @Schema(description = "참여 중인 챌린지 있는지")
    private Boolean exist;  // 참여 중인 챌린지 있는지

    @Schema(description = "챌린지 참여 기록 있는지")
    private Boolean record;  // 챌린지 참여 기록 있는지

    @Schema(description = "참여 가능 챌린지")
    private List<GetChallengeSummaryRes> challengeSummary;  // 참여 가능 챌린지

    @Schema(description = "참여 중인 챌린지 리스트")
    private List<GetChallengeSummaryRes> challengeJoinSummary;  // 참여 중인 챌린지 리스트

    @Schema(description = "이번주 참여 횟수")
    private int count;  // 이번주 참여 횟수

}
