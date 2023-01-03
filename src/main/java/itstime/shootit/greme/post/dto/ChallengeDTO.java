package itstime.shootit.greme.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import itstime.shootit.greme.challenge.dto.GetChallengeList;
import itstime.shootit.greme.challenge.dto.GetChallengeSummaryRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

    @Schema(description = "해당 챌린지에 참여하는지")
    private boolean status;

    @Schema(description = "챌린지 참여 목록")
    private List<GetChallengeList> getChallengeLists;

    @Schema(description = "해당 챌린지 정보")
    private GetChallengeSummaryRes summaryRes;
}
