package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.ChallengePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengePostRepository extends JpaRepository<ChallengePost, Long> {
}
