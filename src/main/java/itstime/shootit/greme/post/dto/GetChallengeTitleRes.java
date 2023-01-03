package itstime.shootit.greme.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface GetChallengeTitleRes {

    @Schema(description = "참여 중인 챌린지 이름")
    String getTitle();
}
