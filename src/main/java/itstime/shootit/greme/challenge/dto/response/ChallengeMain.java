package itstime.shootit.greme.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeMain {
    @Schema(description = "참여한 챌린지가 있다면 true, 참여한 챌린지가 없거나 마감기한 조건에 맞지 않는다면 false")
    private Boolean exist;

    @Schema(description = "exist가 true라면 내가 참여한 챌린지 중 인기 챌린지, exist가 false라면 인기챌린지")
    private ChallengeTitle participatingChallenge;

    @Schema(description = "전체 인기 챌린지")
    private ChallengeTitle popularityChallenge;
}
