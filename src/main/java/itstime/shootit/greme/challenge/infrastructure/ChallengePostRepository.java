package itstime.shootit.greme.challenge.infrastructure;

import itstime.shootit.greme.challenge.domain.ChallengePost;
import itstime.shootit.greme.challenge.dto.response.GetChallengeListRes;
import itstime.shootit.greme.challenge.dto.response.GetChallengeTitleRes;
import itstime.shootit.greme.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengePostRepository extends JpaRepository<ChallengePost, Long> {

    @Query(value = "SELECT p.id, p.image FROM ChallengePost cp LEFT OUTER JOIN ChallengeUser cu on cu.challenge_id = cp.challenge_id LEFT OUTER JOIN Post p on p.id = cp.post_id WHERE cu.challenge_id = :challenge_id", nativeQuery = true)
    List<GetChallengeListRes> findAllImageByChallengeId(@Param("challenge_id") Long challenge_id);

    @Query(value = "SELECT c.title FROM ChallengePost cp LEFT OUTER JOIN Challenge c on c.id = cp.challenge_id " +
            "LEFT OUTER JOIN Post p on p.id = cp.post_id WHERE p.id = :post_id", nativeQuery = true)
    GetChallengeTitleRes findChallengeTitle(Long post_id);

    @Modifying
    @Query(value = "DELETE FROM ChallengePost cp WHERE cp.post=:post")
    void deleteTempByPostId(@Param("post") Post post);
}
