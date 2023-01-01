package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.GetChallengeSummaryRes;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline  FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id\n" +
            "WHERE c.c_id NOT IN (SELECT cu.challenge_id FROM ChallengeUser cu WHERE cu.user_id = :userId) ORDER BY c.cur_num DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindChallenge(Long userId);

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindJoinChallenge(Long userId);

    @Query(value = "SELECT c.title, c.info, c.cur_num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId AND cu.createdDate BETWEEN date_format(now(), '%Y-%m-01') AND date_format(now(), '%Y-%m-%d %H:%i:%s') ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> findRecentJoinChallenge(Long userId);

    ChallengeUser findByChallengeIdAndUserId(Long challengeId, Long userId);

    @Query(value = "SELECT c.id, c.title FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<ChallengeTitle> findJoinChallengeTitle(Long userId);
}
