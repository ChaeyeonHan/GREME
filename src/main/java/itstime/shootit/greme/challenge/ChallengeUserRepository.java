package itstime.shootit.greme.challenge;

import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.ChallengeSummary;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline  FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id\n" +
            "WHERE c.c_id NOT IN (SELECT cu.challenge_id FROM ChallengeUser cu WHERE cu.user_id = :userId) ORDER BY c.cur_num DESC;", nativeQuery = true)
    List<ChallengeSummary> mfindChallenge(Long userId);

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<ChallengeSummary> mfindJoinChallenge(Long userId);

    ChallengeUser findByChallengeIdAndUserId(Long challengeId, Long userId);

    @Query(value = "SELECT c.id, c.title FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<ChallengeTitle> findJoinChallengeTitle(Long userId);
}
