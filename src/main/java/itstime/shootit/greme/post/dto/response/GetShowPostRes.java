package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import itstime.shootit.greme.challenge.dto.response.GetChallengeTitleRes;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetShowPostRes {
    @Schema(description = "유저 고유 번호")
    private Long userId;

    @Schema(description = "해당 다이어리 작성자 이름")
    private String username;

    @Schema(description = "다이어리 사진")
    private String image;

    @Schema(description = "다이어리 내용")
    private String content;

    @Schema(description = "다이어리 해시태그")
    private String hashtag;

    @Schema(description = "다이어리 작성일자")
    private Timestamp createdDate;

    @Schema(description = "다이어리가 해당하는 챌린지 이름")
    private GetChallengeTitleRes challengeTitle;

}
