package itstime.shootit.greme.user.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private String nickName;
    private String email;
}
