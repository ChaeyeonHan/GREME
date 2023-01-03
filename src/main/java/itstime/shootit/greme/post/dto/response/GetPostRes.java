package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface GetPostRes {

    @Schema(description = "다이어리 고유 번호")
    Long getId();

    @Schema(description = "다이어리 사진")
    String image();

}
