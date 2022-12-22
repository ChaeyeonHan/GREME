package itstime.shootit.greme.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import itstime.shootit.greme.common.error.exception.BusinessException;
import itstime.shootit.greme.oauth.application.JwtTokenProvider;
import itstime.shootit.greme.user.application.UserService;
import itstime.shootit.greme.user.dto.request.SignUpReq;
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
            parameters = {@Parameter(name = "username", description = "유저 닉네임")}
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
                    @ApiResponse(responseCode = "422", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = BusinessException.class))),
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

}
