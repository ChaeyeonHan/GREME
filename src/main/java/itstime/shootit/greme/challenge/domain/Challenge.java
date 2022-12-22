package itstime.shootit.greme.challenge.domain;

import itstime.shootit.greme.user.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String info;

    private int cur_num;

    private Date deadline;

    @OneToMany(mappedBy = "challenge")
    private List<ChallengeUser> challengeUsers;

}
