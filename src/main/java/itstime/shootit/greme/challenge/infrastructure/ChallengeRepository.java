package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

//    <T> findById(Long challengId, Class<T> type);

    Optional<Challenge> findById(Long challengeId);

    GetChallengeSummaryRes findById(Long challengeId, Class<GetChallengeSummaryRes> getChallengeSummaryResClass);
}
