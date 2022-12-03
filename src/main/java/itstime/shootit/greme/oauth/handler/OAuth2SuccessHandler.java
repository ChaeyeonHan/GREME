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
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("SUCCESS_LOGIN: " + oAuth2User.getAttribute("email"));


        //먼저 user테이블에 이메일로 조회하고 값이 있다면 jwt로 토큰을 생성해서 header에 저장하고 유저정보를 request에 저장해서 넘긴다.

        //회원이 아니라면 이메일만 리턴한다.
        request.setAttribute("email", oAuth2User.getAttribute("email"));
        response.sendRedirect("/user/login");
    }
}