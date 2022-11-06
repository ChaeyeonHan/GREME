package itstime.shootit.greme.oauth.handler;

import itstime.shootit.greme.oauth.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final CustomOauth2UserService customOauth2UserService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User=(OAuth2User)authentication.getPrincipal();
        System.out.println("SUCCESS_LOGIN: "+ oAuth2User);

        //유저 정보 파싱하고 db에 저장
        customOauth2UserService.saveUser(oAuth2User);

        // 마지막으로 jwt를 생성해서 response.setHeader("token",Jwt); 로 해서 사용자에게 보내준다.
    }
}
