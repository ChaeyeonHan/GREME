package itstime.shootit.greme.challenge;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.GetChallengeRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengUserRepository extends JpaRepository<ChallengeUser, Long> {

    @Query(value = "SELECT *  FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.id = cu.challenge_id\n" +
            "WHERE c.id NOT IN (SELECT cu.challenge_id FROM ChallengeUser cu WHERE cu.user_id=:userId) ORDER BY c.cur_num DESC;", nativeQuery = true)
    List<Challenge> mfindChallenge(Long userId);

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<Challenge> mfindJoinChallenge(Long userId);
}
