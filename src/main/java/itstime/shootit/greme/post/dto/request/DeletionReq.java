package itstime.shootit.greme.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeletionReq {
    @Schema(description = "다이어리 고유 번호")
    private Long id;
}
