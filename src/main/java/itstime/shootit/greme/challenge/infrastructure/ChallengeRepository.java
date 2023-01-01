package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
