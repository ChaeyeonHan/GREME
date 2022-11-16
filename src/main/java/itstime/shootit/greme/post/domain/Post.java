package itstime.shootit.greme.post.domain;

import itstime.shootit.greme.user.BaseEntity;
import itstime.shootit.greme.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageList;

    private String content;

    // 해시태그 검색기능?

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    @OneToMany(mappedBy = "post")
    private List<Likes> likes;

    private boolean status;

}
