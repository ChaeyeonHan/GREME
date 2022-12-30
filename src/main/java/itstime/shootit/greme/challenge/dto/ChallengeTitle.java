package itstime.shootit.greme.challenge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ChallengeTitle {
    @Schema(description = "챌린지 고유 번호")
    Long getId();

    @Schema(description = "챌린지 제목")
    String getTitle();
}
