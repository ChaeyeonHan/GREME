package itstime.shootit.greme.challenge.domain;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.user.BaseEntity;
import itstime.shootit.greme.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "challenge_user")
@Getter
@Entity
public class ChallengeUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
