package itstime.shootit.greme.oauth.application;

import itstime.shootit.greme.oauth.dto.OAuth2Attributes;
import itstime.shootit.greme.user.domain.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

//로그인 성공 시 실행되는 메소드
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static final String ATTRIBUTE_KEY = "id"; //kakao, naver key가 id로 동일하게 한다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //DefaultOAuth2UserService는 OAuth2UserService의 구현체
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //소셜 사이트가 어떤 것인지
        String attributeKey = userRequest.getClientRegistration()         //카카오는 "id", 네이버는 "response"
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();


        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId, attributeKey, oAuth2User.getAttributes());

        return new DefaultOAuth2User(
                //Collections.singleton(new SimpleGrantedAuthority("user")) //사용자 권한(시큐리티에서 식별한 권한인데 jwt이므로 필요 없음) 그래서 null
                null,
                oAuth2Attributes.getAttributes(), //사용자 정보
                ATTRIBUTE_KEY
        );
    }

    //db에 사용자 정보 파싱해서 저장
    public void saveUser(OAuth2User oAuth2User){
        Map<String,Object> profile = oAuth2User.getAttributes();
        String email=(String)profile.get("email");

        User user= User.builder()
                .email(email)
                .build();
        //UserRepository 만들어서 save하면 됨.
    }
}