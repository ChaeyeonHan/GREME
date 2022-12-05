package itstime.shootit.greme.oauth.presentation;

import itstime.shootit.greme.oauth.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @GetMapping("/login/success")
    public LoginResponse getUserInfo(HttpServletRequest request) {
        if (request.getHeader("accessToken") == null) {
            return new LoginResponse((String) request.getAttribute("email"));
        }
        return new LoginResponse((String) request.getAttribute("email")); //이미 회원이라면 request에 유저에 대한 정보들이 있을 것이다. 그거를 담아서 넘기자
    }
}
