package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;

public interface GetPostSummaryRes {
    @Schema(description = "해당 다이어리 작성자 이름")
    String getUsername();

    @Schema(description = "다이어리 사진")
    String getImage();

    @Schema(description = "다이어리 내용")
    String getContent();

    @Schema(description = "다이어리 해시태그")
    String getHashtag();

    @Schema(description = "다이어리 작성일자")
    Timestamp getCreatedDate();
}
