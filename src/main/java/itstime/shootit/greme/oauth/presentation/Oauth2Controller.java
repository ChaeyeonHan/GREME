package itstime.shootit.greme.oauth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import itstime.shootit.greme.oauth.application.OAuth2Service;
import itstime.shootit.greme.oauth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class Oauth2Controller {
    private final OAuth2Service oAuth2Service;

    @Operation(summary = "소셜 로그인",
            parameters = {
                    @Parameter(name = "accessToken", description = "액세스 토큰"),
                    @Parameter(name = "domain", description = "naver or kakao")
    })
    @GetMapping("/login")
    public LoginResponse login(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("domain") String domain) {
        return oAuth2Service.findEmailByAccessToken(domain, accessToken);
    }

}
