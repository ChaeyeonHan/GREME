package itstime.shootit.greme.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import itstime.shootit.greme.aws.application.S3Uploader;
import itstime.shootit.greme.oauth.application.JwtTokenProvider;
import itstime.shootit.greme.post.application.PostService;
import itstime.shootit.greme.post.dto.request.ChangeReq;
import itstime.shootit.greme.post.dto.response.GetShowPostRes;
import itstime.shootit.greme.post.dto.request.CreationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final S3Uploader s3Uploader;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "다이어리 작성",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping("")
    public ResponseEntity<Void> create(
            @RequestPart CreationReq creationReq,
            @RequestPart MultipartFile multipartFile,
            @RequestHeader("accessToken") String accessToken
    ) {
        List<String> fileNames = s3Uploader.uploadFile(List.of(multipartFile));

        postService.create(creationReq, fileNames, jwtTokenProvider.getEmail(accessToken));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "다른 유저의 다이어리 조회하기", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰"),
            @Parameter(name = "postId", description = "조회하려는 다이어리 id")})
    @GetMapping("/{postId}")
    public GetShowPostRes showPost(@PathVariable Long postId, @RequestHeader("accessToken") String accessToken) {
        return postService.showPost(jwtTokenProvider.getEmail(accessToken), postId);
    }

    @Operation(summary = "다이어리 수정",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 또는 다이어리", content = @Content(schema = @Schema(implementation = String.class)))})
    @PutMapping("")
    public ResponseEntity<Void> update(
            @RequestPart ChangeReq changeReq,
            @RequestPart MultipartFile multipartFile,
            @RequestHeader("accessToken") String accessToken) {
        String imageUrl = postService.findImageUrl(changeReq.getPostId()); //기존 이미지 조회
        List<String> fileNames = s3Uploader.uploadFile(List.of(multipartFile)); //수정할 이미지 업로드
        postService.updateById(changeReq, fileNames, jwtTokenProvider.getEmail(accessToken)); //post 수정
        s3Uploader.deleteFile(imageUrl); //기존 이미지 삭제
        /*
        기존 이미지를 삭제 했는데 예외가 발생할 경우 post 수정된 것이 롤백이 되지 않는다.
        postService에 s3Uploader을 선언하기에는 DDD에 어긋?
        추후 고민해보는 걸로...
        */
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
