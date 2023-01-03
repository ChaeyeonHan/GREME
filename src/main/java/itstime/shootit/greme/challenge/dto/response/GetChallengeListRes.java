package itstime.shootit.greme.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface GetChallengeListRes {

    @Schema(description = "챌린지 고유 번호")
    Long getId();

    @Schema(description = "해당 챌린지에 참여한 다이어리 사진")
    String getImage();
}
