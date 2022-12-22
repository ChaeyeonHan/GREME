package itstime.shootit.greme.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReq {
    private Integer genderType;
    private String area;
    private String purpose;
}
