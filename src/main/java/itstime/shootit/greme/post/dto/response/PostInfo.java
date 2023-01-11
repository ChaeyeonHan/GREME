package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostInfo {
    @Schema(description = "다이어리 고유 번호")
    private Long id;

    @Schema(description = "이미지 url")
    private String image;
}
