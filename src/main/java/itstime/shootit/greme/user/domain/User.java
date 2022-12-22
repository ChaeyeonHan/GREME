package itstime.shootit.greme.user.domain;

import itstime.shootit.greme.challenge.ChallengeUser;
import itstime.shootit.greme.user.BaseEntity;
import itstime.shootit.greme.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;  // 회원가입시 유저 id

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Interest> interest; // 관심사

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String area;  // "서울특별시 강남구"? 아님 따로 저장?

    private String purpose;

    private String profileImg;

    @OneToMany(mappedBy = "user")
    private List<ChallengeUser> challengeUsers;

    public void updateGender(Integer genderType) {
        switch (genderType){
            case 0:
                gender=Gender.MALE;
                return;
            case 1:
                gender=Gender.FEMALE;
                return;
            case 2:
                gender=Gender.ANYTHING;
                return;
        }
    }

    public void updateArea(String area){
        this.area=area;
    }

    public void updatePurpose(String purpose){
        this.purpose=purpose;
    }

}
