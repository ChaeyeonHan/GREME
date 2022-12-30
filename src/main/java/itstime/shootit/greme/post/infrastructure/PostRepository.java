package itstime.shootit.greme.post.infrastructure;

import itstime.shootit.greme.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
