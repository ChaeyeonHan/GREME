package itstime.shootit.greme.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllPostRes {
    @Schema(description = "날짜")
    private String date;

    @Schema(description = "다이어리 정보")
    private List<PostInfo> postInfos;
}