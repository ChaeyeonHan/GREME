package itstime.shootit.greme.post.domain;

import itstime.shootit.greme.challenge.domain.ChallengePost;
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
@Table(name = "post")
@Builder
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String image;

    private String content;

    private String hashtag;

    private boolean status;  // 공개여부

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<ChallengePost> challengePosts;

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void updateStatus(boolean status) {
        this.status = status;
    }
}
