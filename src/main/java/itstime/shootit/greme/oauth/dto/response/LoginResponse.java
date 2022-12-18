package itstime.shootit.greme.oauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginResponse {
    private String email;
    //유저에 대한 각종 정보들
}