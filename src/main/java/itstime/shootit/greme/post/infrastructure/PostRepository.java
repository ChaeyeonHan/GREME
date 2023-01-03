package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.response.PostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id,p.image,p.content,p.hashtag,p.status FROM post p " +
            "WHERE date_format(p.created_date, '%Y-%m-%d')=:date AND p.user_id=:userId", nativeQuery = true)
    Optional<PostRes> findPostByUserAndDate(@Param("userId") Long userId, @Param("date")String date);

}
