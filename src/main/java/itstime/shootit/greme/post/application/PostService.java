package itstime.shootit.greme.post.application;

import itstime.shootit.greme.challenge.ChallengeRepository;
import itstime.shootit.greme.challenge.ChallengeUserRepository;
import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.post.dto.request.CreationReq;
import itstime.shootit.greme.post.dto.response.PostRes;
import itstime.shootit.greme.post.exception.NotExistsPostException;
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
    private final ChallengeUserRepository challengeUserRepository;

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

        challengeUserRepository.saveAll(challenges.stream() //challenge에 post등록
                .map(challenge -> ChallengeUser.builder()
                        .challenge(challenge)
                        .post(post)
                        .build())
                .collect(Collectors.toList()));
    }


    public PostRes findByDate(String date, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        return postRepository.findPostByUserAndDate(user.getId(), date)
                .orElseThrow(NotExistsPostException::new);
    }
}
