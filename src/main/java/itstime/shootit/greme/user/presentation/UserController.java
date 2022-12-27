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
import itstime.shootit.greme.user.dto.request.InterestReq;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.dto.request.UserInfoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "중복된 닉네임인지 조회",
            parameters = {@Parameter(name = "username", description = "유저 닉네임")},
            responses = {@ApiResponse(responseCode = "409", description = "중복된 닉네임", content = @Content(schema = @Schema(implementation = String.class)))}
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
                    @ApiResponse(responseCode = "200", description = "회원가입 성공", headers = {@Header(name = "accessToken", description = "액세스 토큰")})
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


}
