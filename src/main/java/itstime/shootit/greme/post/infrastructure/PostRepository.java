package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.response.GetPostSummaryRes;
import itstime.shootit.greme.post.dto.response.GetPostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id, p.image FROM Post p WHERE p.user_id = :userId AND p.status = true ORDER BY p.createdDate DESC LIMIT 5;", nativeQuery = true)
    List<GetPostRes> findRecentPostByUserEmail(Long userId);

    @Query(value = "SELECT u.username, p.image, p.content, p.hashtag, p.createdDate FROM Post p LEFT OUTER JOIN User u on u.id = p.user_id WHERE p.id = :post_id", nativeQuery = true)
    GetPostSummaryRes findOnePost(Long post_id);

    @Query(value = "SELECT p.image FROM Post p WHERE p.id=:post_id", nativeQuery = true)
    Optional<String> findImageById(@Param("post_id") Long post_id);
}
