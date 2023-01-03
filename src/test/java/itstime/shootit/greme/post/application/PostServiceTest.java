package itstime.shootit.greme.post.application;


import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.response.PostRes;
import itstime.shootit.greme.post.infrastructure.PostRepository;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;


    @Test
    @DisplayName("날짜로 글 조회")
    void findPostByUserAndDate() {
        //given
        User user = User.builder()
                .username("test")
                .email("email")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .content("content")
                .hashtag("hashtag")
                .image("file")
                .status(true)
                .build();
        postRepository.save(post);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createdDate = simpleDateFormat.format(post.getCreatedDate());

        //when
        PostRes postResponse = postService.findByDate(createdDate, "email");

        //then
        assertEquals(true, postResponse.getStatus());
    }
}