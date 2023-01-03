package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.dto.GetChallengeList;
import itstime.shootit.greme.challenge.dto.GetChallengeSummaryRes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

//    <T> findById(Long challengId, Class<T> type);

    Optional<Challenge> findById(Long challengeId);

    GetChallengeSummaryRes findById(Long challengeId, Class<GetChallengeSummaryRes> getChallengeSummaryResClass);
}
