package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

    @Query(value = "SELECT DISTINCT c.id, c.title, c.info, c.num, c.deadline  FROM challenge c LEFT OUTER JOIN challenge_user cu on c.id = cu.challenge_id\n" +
            "WHERE c.id NOT IN (SELECT cu.challenge_id FROM challenge_user cu WHERE cu.user_id = :userId) ORDER BY c.num DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindChallenge(Long userId);  // 참여 가능 챌린지

    @Query(value = "SELECT DISTINCT c.id, c.title, c.info, c.num, c.deadline FROM challenge c LEFT OUTER JOIN challenge_user cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> mfindJoinChallenge(Long userId);  // 참여 중인 챌린지

    @Query(value = "SELECT c.title, c.info, c.num, c.deadline FROM challenge c LEFT OUTER JOIN challenge_user cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId AND cu.created_date BETWEEN date_format(now(), '%Y-%m-01') AND date_format(now(), '%Y-%m-%d %H:%i:%s') ORDER BY c.deadline DESC;", nativeQuery = true)
    List<GetChallengeSummaryRes> findRecentJoinChallenge(Long userId);  // 이번달에 참여한 챌린지

    ChallengeUser findByChallengeIdAndUserId(Long challengeId, Long userId);  // 참여하는 챌린지인지

    List<ChallengeUser> findAllByUserId(Long userId);
    boolean existsByChallengeIdAndUserId(Long challengeId, Long userId);  // 참여하는 챌린지인지

    @Query(value = "SELECT c.id, c.title FROM challenge c LEFT OUTER JOIN challenge_user cu on c.c_id = cu.challenge_id " +
            "WHERE cu.user_id = :userId ORDER BY c.deadline DESC;", nativeQuery = true)
    List<ChallengeTitle> findJoinChallengeTitle(Long userId);

    Boolean existsByUserId(Long user_id);

}
