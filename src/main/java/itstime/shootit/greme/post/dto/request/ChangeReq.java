package itstime.shootit.greme.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeReq {
    @Schema(description = "다이어리 고유 번호")
    private Long postId;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "해시태그")
    private String hashtag;

    @Schema(description = "챌린지 고유 번호")
    private Long challenge;

    @Schema(description = "공개여부 true or false")
    private boolean status;
}
