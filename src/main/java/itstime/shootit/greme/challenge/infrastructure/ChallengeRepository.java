package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

//    <T> findById(Long challengId, Class<T> type);

    Optional<Challenge> findById(Long challengeId);

    GetChallengeSummaryRes findById(Long challengeId, Class<GetChallengeSummaryRes> getChallengeSummaryResClass);

    @Query(value = "UPDATE challenge c SET c.num = c.num -1 WHERE c.id in :challengeIds", nativeQuery = true)
    void numDeleted(List<Long> challengeIds);

    @Query(value = "SELECT c.id FROM challenge c INNER JOIN challenge_user cu ON c.id=cu.challenge_id WHERE cu.user_id=:userId ORDER BY c.id DESC", nativeQuery = true)
    List<Long> findParticipatingChallenge(@Param("userId") Long userId);

    @Query(value = "SELECT c.id, c.title FROM challenge c LEFT JOIN challenge_post cp ON c.id=cp.challenge_id GROUP BY cp.challenge_id ORDER BY COUNT(cp.id) DESC LIMIT 1", nativeQuery = true)
    ChallengeTitle findPopularityChallenge();

    @Query(value = "SELECT c.id, c.title FROM challenge c INNER JOIN challenge_post cp ON c.id=cp.challenge_id WHERE cp.challenge_id IN :myChallenges GROUP BY cp.challenge_id ORDER BY COUNT(cp.id) DESC LIMIT 1", nativeQuery = true)
    ChallengeTitle findParticipatingPopularityChallenge(@Param("myChallenges") List<Long> myChallenges);

    @Query(value = "SELECT c.id FROM challenge c INNER JOIN challenge_user cu ON c.id=cu.challenge_id GROUP BY cu.challenge_id ORDER BY COUNT(cu.id) DESC LIMIT 1", nativeQuery = true)
    Long findPopularityChallengeId();

    @Query(value = "SELECT c.id FROM challenge c ORDER BY c.id DESC LIMIT 1", nativeQuery = true)
    Long findCurrentChallengeId();

    @Query(value = "SELECT c.id, c.title, c.info, c.num, c.deadline FROM challenge c INNER JOIN challenge_user cu on c.id = cu.challenge_id " +
            "WHERE cu.user_id = :userId AND MONTH(cu.created_date) = MONTH(CURRENT_DATE()) AND YEAR(cu.created_date)=YEAR(CURRENT_DATE()) ORDER BY c.deadline DESC;", nativeQuery = true)
    Optional<List<GetChallengeSummaryRes>> findMonthJoinChallenge(@Param("userId") Long userId);
}
