package itstime.shootit.greme.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import itstime.shootit.greme.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "스웨거 테스트",
            parameters = {@Parameter(name = "temp", description = "임시 파라미터 입니다.")}
    )
    @GetMapping("")
    public String swwagerTest(
            @RequestParam("temp") String temp
    ) {
        return "swagger";
    }

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

}
