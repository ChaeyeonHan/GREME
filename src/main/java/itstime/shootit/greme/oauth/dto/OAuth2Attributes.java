package itstime.shootit.greme.oauth.dto;


import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
@Builder
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String nickName;
    private String email;

    public static OAuth2Attributes of(
            String registrationId
            , String attributeKey
            , Map<String, Object> attributes
    ){
        if ("kakao".equals(registrationId)) {
            return ofKakao(attributeKey, attributes);
        }
        return null;
    }

    private static OAuth2Attributes ofKakao(
            String attributeKey
            , Map<String, Object> attributes
    ) {
        //attributes 매개변수 안에 사용자 정보들이 담겨 있음!
        //attributes: {id=2475391272, connected_at=2022-10-07T09:32:10Z
        // , properties={nickname=김상인},
        // kakao_account={profile_nickname_needs_agreement=false, profile={nickname=김상인}}}

        Map<String,Object> kakaoAccount = (Map<String,Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        kakaoAccount.put(attributeKey,attributes.get(attributeKey));

        return OAuth2Attributes.builder()
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .nickName((String)kakaoProfile.get("nickname"))
                .email((String)kakaoProfile.get("email"))
                .build();
    }
}
