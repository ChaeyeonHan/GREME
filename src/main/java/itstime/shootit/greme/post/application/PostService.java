package itstime.shootit.greme.post.application;

import itstime.shootit.greme.challenge.infrastructure.ChallengeRepository;
import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.domain.ChallengePost;
import itstime.shootit.greme.challenge.infrastructure.ChallengePostRepository;
import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.GetChallengeTitleRes;
import itstime.shootit.greme.post.dto.GetPost;
import itstime.shootit.greme.post.dto.GetShowPostRes;
import itstime.shootit.greme.post.dto.request.CreationReq;
import itstime.shootit.greme.post.infrastructure.PostRepository;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengePostRepository challengePostRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(CreationReq creationReq, List<String> fileNames, String email) {
        System.out.println("FILENAME: " + fileNames.get(0));

        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        List<Challenge> challenges = challengeRepository.findAllById(creationReq.getChallenges()); //게시글에 등록한 챌린지 조회

        Post post = Post.builder()
                .user(user)
                .content(creationReq.getContent())
                .hashtag(creationReq.getHashtag())
                .image(fileNames.get(0))
                .status(creationReq.isStatus())
                .build();
        postRepository.save(post); //게시글 저장

        challengePostRepository.saveAll(challenges.stream() //challenge에 post등록
                .map(challenge -> ChallengePost.builder()
                        .challenge(challenge)
                        .post(post)
                        .build())
                .collect(Collectors.toList()));
    }

    public GetShowPostRes showPost(String email, Long post_id){
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);
        GetChallengeTitleRes challengeTitle = postRepository.findChallengeTitle(post_id);  // 해당 챌린지 title 가져오기

        GetPost getPost = postRepository.findOnePost(post_id);

        return GetShowPostRes.builder()
                .username(user.getUsername())
                .image(getPost.getImage())
                .content(getPost.getContent())
                .hashtag(getPost.getHashtag())
                .createdDate(getPost.getCreatedDate())
                .challengeTitle(challengeTitle).build();
    }
}
