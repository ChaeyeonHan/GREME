package itstime.shootit.greme.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import itstime.shootit.greme.common.error.exception.BusinessException;
import itstime.shootit.greme.oauth.application.JwtTokenProvider;
import itstime.shootit.greme.user.application.UserService;
import itstime.shootit.greme.user.dto.request.*;
import itstime.shootit.greme.user.dto.response.ConfigurationRes;
import itstime.shootit.greme.user.dto.response.ProfileRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "중복된 닉네임인지 조회",
            parameters = {@Parameter(name = "username", description = "유저 닉네임")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "409", description = "중복된 닉네임", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @GetMapping("/username/check")
    public ResponseEntity<Void> existsUsername(@RequestParam("username") String username) {
        userService.checkExistsUsername(username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "422", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "204", description = "회원가입 성공", headers = {@Header(name = "accessToken", description = "액세스 토큰")})
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpReq signUpReq) {
        userService.signUp(signUpReq);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header("accessToken", jwtTokenProvider.createAccessToken(signUpReq.getEmail()))
                .build();
    }

    @Operation(summary = "관심사 수정",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PatchMapping("/interest")
    public ResponseEntity<Void> updateInterest(
            @RequestHeader("accessToken") String accessToken,
            @RequestBody InterestReq interestReq
    ) {
        userService.updateInterest(jwtTokenProvider.getEmail(accessToken), interestReq);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "사용자 추가 정보 입력",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PatchMapping("/info")
    public ResponseEntity<Void> updateUserInfo(
            @RequestHeader("accessToken") String accessToken,
            @RequestBody UserInfoReq userInfoReq
    ) {
        userService.updateInfo(jwtTokenProvider.getEmail(accessToken), userInfoReq);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    @Operation(summary = "회원 탈퇴하기", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")})
    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@RequestHeader("accessToken") String accessToken) {
        userService.deleteUser(jwtTokenProvider.getEmail(accessToken));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

    @Operation(summary = "프로필 수정1",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PatchMapping("/profile1")
    public ResponseEntity<Void> updateProfile1(
            @RequestHeader("accessToken") String accessToken,
            @RequestBody Profile1Req profile1Req
    ) {
        userService.updateProfile1(jwtTokenProvider.getEmail(accessToken), profile1Req);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "프로필 수정2",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PatchMapping("/profile2")
    public ResponseEntity<Void> updateProfile2(
            @RequestHeader("accessToken") String accessToken,
            @RequestBody Profile2Req profile2Req
    ) {
        userService.updateProfile2(jwtTokenProvider.getEmail(accessToken), profile2Req);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "프로필 이미지 밴경",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PatchMapping("/profile-image")
    public ResponseEntity<Void> updateProfileImage(
            @RequestHeader("accessToken") String accessToken,
            @RequestPart(value = "multipartFile") MultipartFile multipartFile
    ) {
        userService.updateProfileImage(jwtTokenProvider.getEmail(accessToken), multipartFile);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "환경설정",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "환경설정 첫 페이지에 대한 데이터", content = @Content(schema = @Schema(implementation = ConfigurationRes.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("")
    public ConfigurationRes getConfiguration(
            @RequestHeader("accessToken") String accessToken
    ) {
        return userService.findConfiguration(jwtTokenProvider.getEmail(accessToken));
    }

    @Operation(summary = "프로필 조회",
            parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "환경설정 첫 페이지에 대한 데이터", content = @Content(schema = @Schema(implementation = ProfileRes.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/profile")
    public ProfileRes getProfile(
            @RequestHeader("accessToken") String accessToken
    ) {
        return userService.findProfile(jwtTokenProvider.getEmail(accessToken));
    }
}
