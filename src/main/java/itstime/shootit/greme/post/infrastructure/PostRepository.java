package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.GetPostRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id, p.image FROM Post p WHERE p.user_id = :userId AND p.status = true ORDER BY p.createdDate DESC LIMIT 5;", nativeQuery = true)
    List<GetPostRes> findRecentPostByUserEmail(Long userId);
}
