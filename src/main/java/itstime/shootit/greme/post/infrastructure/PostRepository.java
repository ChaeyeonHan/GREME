package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.challenge.dto.response.GetChallengeListRes;
import itstime.shootit.greme.post.domain.Post;

import itstime.shootit.greme.post.dto.query.PostInfoQuery;

import itstime.shootit.greme.post.dto.query.SearchPostInfo;
import itstime.shootit.greme.post.dto.response.GetPostSummaryRes;
import itstime.shootit.greme.post.dto.response.GetPostRes;
import itstime.shootit.greme.post.dto.response.PostRes;
import itstime.shootit.greme.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT p.id,p.image,p.content,p.hashtag,p.status FROM post p " +
            "WHERE date_format(p.created_date, '%Y-%m-%d')=:date AND p.user_id=:userId", nativeQuery = true)
    Optional<PostRes> findPostByUserAndDate(@Param("userId") Long userId, @Param("date") String date);

    @Query(value = "SELECT p.id, p.image FROM post p WHERE p.user_id = :userId AND p.status = true ORDER BY p.created_date DESC LIMIT 5;", nativeQuery = true)
    List<GetPostRes> findRecentPostByUserEmail(Long userId);

    @Query(value = "SELECT u.id as userId, u.username, p.image, p.content, p.hashtag, p.created_date as createdDate FROM post p LEFT OUTER JOIN user u on u.id = p.user_id WHERE p.id = :post_id", nativeQuery = true)
    GetPostSummaryRes findOnePost(@Param("post_id") Long post_id);

    @Query(value = "SELECT p.image FROM post p WHERE p.id=:post_id", nativeQuery = true)
    Optional<String> findImageById(@Param("post_id") Long post_id);

    void deleteByIdAndUser(Long id, User user);

    @Query(value = "SELECT p.id, p.image, date_format(p.created_date, '%Y-%m') as createdDate FROM post p WHERE p.user_id=:user_id ORDER BY p.created_date DESC", nativeQuery = true)
    List<PostInfoQuery> findAllByUserOrderByCreatedDate(@Param("user_id") Long user_id);

    List<Post> findAllByUser(User user);

    @Query(value = "SELECT p.id, p.image FROM post p WHERE p.status=:status AND (p.content LIKE %:search% OR p.hashtag LIKE %:search%) ORDER BY created_date DESC", nativeQuery = true)
    List<SearchPostInfo> findBySearch(@Param("status") boolean status, @Param("search") String search);

    @Query(value = "SELECT p.id, p.image from post p INNER JOIN challenge_post cp ON p.id = cp.post_id WHERE cp.challenge_id = :challenge_id AND p.status = true", nativeQuery = true)
    List<GetChallengeListRes> findAllImageByChallengeId(@Param("challenge_id") Long challenge_id);

}
