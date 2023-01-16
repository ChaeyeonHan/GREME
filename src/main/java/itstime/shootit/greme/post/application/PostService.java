package itstime.shootit.greme.post.application;


import itstime.shootit.greme.challenge.domain.ChallengePost;
import itstime.shootit.greme.challenge.infrastructure.ChallengePostRepository;
import itstime.shootit.greme.challenge.infrastructure.ChallengeRepository;

import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.post.domain.Post;
import itstime.shootit.greme.challenge.dto.response.GetChallengeTitleRes;
import itstime.shootit.greme.post.dto.query.PostInfoQuery;
import itstime.shootit.greme.post.dto.request.ChangeReq;
import itstime.shootit.greme.post.dto.request.DeletionReq;
import itstime.shootit.greme.post.dto.response.AllPostRes;
import itstime.shootit.greme.post.dto.response.GetPostSummaryRes;
import itstime.shootit.greme.post.dto.response.GetShowPostRes;
import itstime.shootit.greme.post.dto.request.CreationReq;

import itstime.shootit.greme.post.dto.response.PostInfo;
import itstime.shootit.greme.post.dto.response.PostRes;

import itstime.shootit.greme.post.exception.NotExistsPostException;
import itstime.shootit.greme.post.infrastructure.PostRepository;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengePostRepository challengePostRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long create(CreationReq creationReq, List<String> fileNames, String email) {
        System.out.println("FILENAME: " + fileNames.get(0));

        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        Challenge challenge = challengeRepository.findById(creationReq.getChallenge())
                .orElseThrow(NotExistsPostException::new); //게시글에 등록한 챌린지 조회

        Post post = Post.builder()
                .user(user)
                .content(creationReq.getContent())
                .hashtag(creationReq.getHashtag())
                .image(fileNames.get(0))
                .status(creationReq.isStatus())
                .build();
        postRepository.save(post); //게시글 저장

        challengePostRepository.save(ChallengePost.builder()
                .challenge(challenge)
                .post(post)
                .build());

        return post.getId();
    }

    public PostRes findByDate(String date, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        return postRepository.findPostByUserAndDate(user.getId(), date)
                .orElseThrow(NotExistsPostException::new);
    }

    public GetShowPostRes showPost(String email, Long post_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);
        GetChallengeTitleRes challengeTitle = challengePostRepository.findChallengeTitle(post_id);  // 해당 챌린지 title 가져오기

        GetPostSummaryRes getPost = postRepository.findOnePost(post_id);

        return GetShowPostRes.builder()
                .username(user.getUsername())
                .image(getPost.getImage())
                .content(getPost.getContent())
                .hashtag(getPost.getHashtag())
                .createdDate(getPost.getCreatedDate())
                .challengeTitle(challengeTitle).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateById(ChangeReq changeReq, List<String> fileNames, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        Post post = postRepository.findById(changeReq.getPostId())
                .orElseThrow(NotExistsPostException::new);

        challengePostRepository.deleteTempByPostId(post); //기존에 등록된 챌린지 삭제

        Challenge challenge = challengeRepository.findById(changeReq.getChallenge())
                .orElseThrow(NotExistsPostException::new); //수정할 챌린지 조회

        post.updateContent(changeReq.getContent());
        post.updateHashtag(changeReq.getHashtag());
        post.updateImage(fileNames.get(0));
        post.updateStatus(changeReq.isStatus());
        postRepository.save(post); //게시글 수정

        challengePostRepository.save(ChallengePost.builder()
                .challenge(challenge)
                .post(post).build());
    }

    @Transactional(readOnly = true)
    public String findImageUrl(Long postId) {
        return postRepository.findImageById(postId)
                .orElseThrow(NotExistUserException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteIdAndEmail(DeletionReq deletionReq, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        postRepository.deleteByIdAndUser(deletionReq.getId(), user);
    }

    public List<AllPostRes> findAllByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        List<PostInfoQuery> postInfoQueries = postRepository.findAllByUserOrderByIdDesc(user.getId()); //사용자가 쓴 최신 다이어리 조회

        /*
        받아온 사용자 다이어리들을
        "2022-12":[{id,image}, {id2,image2}]
        "2023-01":[{id3,image3}, {id4,image4}]
        이런 구조로 변환시켜서 데이터를 응답함.
         */
        Map<String, List<PostInfo>> map = new LinkedHashMap<>();
        for (PostInfoQuery postInfoQuery : postInfoQueries) {
            String createdDate = postInfoQuery.getCreatedDate();
            map.computeIfAbsent(createdDate, key -> new ArrayList<>())
                    .add(new PostInfo(postInfoQuery.getId(), postInfoQuery.getImage()));
        }

        List<AllPostRes> allPosts = new ArrayList<>();
        map.forEach((createdDate, postInfos) -> allPosts.add(new AllPostRes(createdDate, postInfos)));

        return allPosts;
    }

    @Transactional(readOnly = true)
    public List<PostInfo> findBySearch(String search) {
        return postRepository.findByContentContainingOrHashtagContaining(search, search);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(User user) {
        return postRepository.findAllByUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAllPosts(User user) {
        postRepository.deleteAll(getAllPosts(user));
        log.info("해당 유저의 다이어리가 모두 삭제되었습니다.");
    }

}
