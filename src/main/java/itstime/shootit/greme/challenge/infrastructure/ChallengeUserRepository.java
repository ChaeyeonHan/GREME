package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    @Query(value = "SELECT c.title, c.info, c.num, c.deadline  FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id\n" +
            "WHERE c.c_id NOT IN (SELECT cu.challenge_id FROM ChallengeUser cu WHERE cu.user_id = :userId) ORDER BY c.num DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindChallenge(Long userId);  // 참여 가능 챌린지

    @Query(value = "SELECT c.title, c.info, c.num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindJoinChallenge(Long userId);  // 참여 중인 챌린지

    @Query(value = "SELECT c.title, c.info, c.num, c.deadline FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId AND cu.createdDate BETWEEN date_format(now(), '%Y-%m-01') AND date_format(now(), '%Y-%m-%d %H:%i:%s') ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> findRecentJoinChallenge(Long userId);  // 이번달에 참여한 챌린지

    ChallengeUser findByChallengeAndUser(Long challengeId, Long userId);  // 참여하는 챌린지인지
    boolean existsByChallengeIdAndUserId(Long challengeId, Long userId);  // 참여하는 챌린지인지

    @Query(value = "SELECT c.id, c.title FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<ChallengeTitle> findJoinChallengeTitle(Long userId);
}
