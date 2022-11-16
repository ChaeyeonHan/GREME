package itstime.shootit.greme.user.domain;

import itstime.shootit.greme.challenge.ChallengeUser;
import itstime.shootit.greme.post.domain.Comment;
import itstime.shootit.greme.post.domain.Likes;
import itstime.shootit.greme.user.BaseEntity;
import itstime.shootit.greme.user.Gender;
import itstime.shootit.greme.user.Purpose;
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

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Interest> interest; // 관심사

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String area;  // "서울특별시 강남구"? 아님 따로 저장?

    @Enumerated(EnumType.ORDINAL)
    private Purpose purpose;

    private String profileImg;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<Likes> likes;

    @OneToMany(mappedBy = "user")
    private List<ChallengeUser> challengeUsers;

}
