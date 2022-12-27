package itstime.shootit.greme.challenge;

import itstime.shootit.greme.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = "SELECT * FROM Challenge c LEFT OUTER JOIN ChallengeUser cu on c.id = cu.challenge_id WHERE cu.createdDate BETWEEN DATE_ADD(NOW(), INTERVAL-1 WEEK) AND NOW() AND cu.user_id = :userId;", nativeQuery = true)
    List<Challenge> findAllByUserId(Long userId);
}
