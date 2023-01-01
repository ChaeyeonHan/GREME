package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.GetChallengeTitleRes;
import itstime.shootit.greme.post.dto.GetPost;
import itstime.shootit.greme.post.dto.GetPostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT c.title FROM ChallengePost cp LEFT OUTER JOIN Challenge c on c.id = cp.challenge_id " +
            "LEFT OUTER JOIN Post p on p.id = cp.post_id WHERE p.id = :post_id", nativeQuery = true)
    GetChallengeTitleRes findChallengeTitle(Long post_id);


    @Query(value = "SELECT p.id, p.image FROM Post p WHERE p.user_id = :userId AND p.status = true ORDER BY p.createdDate DESC LIMIT 5;", nativeQuery = true)
    List<GetPostRes> findRecentPostByUserEmail(Long userId);

    @Query(value = "SELECT u.username, p.image, p.content, p.hashtag, p.createdDate FROM Post p LEFT OUTER JOIN User u on u.id = p.user_id WHERE p.id = :post_id", nativeQuery = true)
    GetPost findOnePost(Long post_id);
}
