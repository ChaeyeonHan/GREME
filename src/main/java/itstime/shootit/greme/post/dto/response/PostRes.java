package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface PostRes {
    @Schema(description = "다이어리 고유 번호")
    Long getId();

    @Schema(description = "이미지 url")
    String getImage();

    @Schema(description = "내용")
    String getContent();

    @Schema(description = "해시태그")
    String getHashtag();

    @Schema(description = "공개여부 true(공개) or false(미공개)")
    boolean getStatus();
}
