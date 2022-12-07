package itstime.shootit.greme.oauth.presentation;

import itstime.shootit.greme.oauth.application.OAuth2Service;
import itstime.shootit.greme.oauth.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class Oauth2Controller {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/login")
    public LoginResponse login(@RequestHeader("accessToken") String accessToken){
        System.out.println("ACCESSTOKEN: "+accessToken);
        return oAuth2Service.findUserByAccessToken(accessToken);
    }

}
