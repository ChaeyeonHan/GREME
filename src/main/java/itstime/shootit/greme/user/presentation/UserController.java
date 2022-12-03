package itstime.shootit.greme.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import itstime.shootit.greme.oauth.dto.response.LoginResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Operation(summary = "스웨거 테스트",
            parameters = {@Parameter(name = "temp", description = "임시 파라미터 입니다.")}
    )
    @GetMapping("")
    public String swwagerTest(
            @RequestParam("temp") String temp
    ) {
        return "swagger";
    }

    @GetMapping("/oauth2-login")
    public LoginResponse getUserInfo(HttpServletRequest request) {
        if (request.getHeader("accessToken") == null) {
            return new LoginResponse((String) request.getAttribute("email"));
        }
        return new LoginResponse((String) request.getAttribute("email")); //이미 회원이라면 request에 유저에 대한 정보들이 있을 것이다. 그거를 담아서 넘기자
    }

}
