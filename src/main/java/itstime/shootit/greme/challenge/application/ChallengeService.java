package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.dto.response.*;
import itstime.shootit.greme.challenge.infrastructure.ChallengePostRepository;
import itstime.shootit.greme.challenge.infrastructure.ChallengeUserRepository;
import itstime.shootit.greme.challenge.infrastructure.ChallengeRepository;
import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import itstime.shootit.greme.challenge.exception.FailAddChallengeException;
import itstime.shootit.greme.post.dto.response.GetPostRes;
import itstime.shootit.greme.post.infrastructure.PostRepository;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.response.GetProfileRes;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
//@Lazy
@Service
public class ChallengeService {

    private final ChallengeUserRepository challengeUserRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengePostRepository challengePostRepository;  // 이 부분 뺄 수 있게 수정해주기
    private final ChallengePostService challengePostService;
    private final ChallengeUserService challengeUserService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<GetChallengeSummaryRes> challenge(Long userId) {  // 참여 가능 챌린지 조회
        if (!userRepository.existsById(userId)) {
            throw new NotExistUserException();
        }

        return challengeUserRepository.mfindChallenge(userId);
    }

    @Transactional(readOnly = true)
    public List<GetChallengeSummaryRes> joinChallenge(Long userId) {  // 참여 중인 챌린지 조회
        if (!userRepository.existsById(userId)) {
            throw new NotExistUserException();
        }

        return challengeUserRepository.mfindJoinChallenge(userId);
    }

    @Transactional(readOnly = true)
    public GetChallengeInfoRes challengeMain(String email) {
        System.out.println("challengeMain");
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);
        System.out.println(user.getUsername());

        return GetChallengeInfoRes.builder()
                .exist(challengeUserService.existsByUserId(user.getId()))  // 참여 중인 챌린지 있는지
                .record(challengePostService.recordExist(user.getId()))  // 챌린지 참여 기록 있는지
                .challengeSummary(challenge(user.getId()))  // 참여 가능 챌린지
                .challengeJoinSummary(joinChallenge(user.getId())).build();
//                .count().build();

    }

    @Transactional(rollbackFor = Exception.class)
    public void addChallenge(String email, Long challengeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);
        if (challengeUserRepository.existsByChallengeIdAndUserId(challengeId, user.getId())) {  // 이미 챌린지 등록되어 있으면
            throw new FailAddChallengeException();
        }
        challengeUserRepository.save(ChallengeUser.builder()
                .challenge(challengeRepository.findById(challengeId).get())
                .user(user)
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChallenge(String email, Long challengeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);
        ChallengeUser challengeUserEntity = challengeUserRepository.findByChallengeIdAndUserId(challengeId, user.getId());

        challengeUserRepository.delete(challengeUserEntity);
    }

    @Transactional(readOnly = true)
    public List<ChallengeTitle> findJoinChallengeTitle(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        return challengeUserRepository.findJoinChallengeTitle(user.getId());
    }

    @Transactional(readOnly = true)
    public GetProfileRes showUserProfile(String email, Long user_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        List<GetChallengeSummaryRes> getChallengeSummaryRes = challengeUserRepository.findRecentJoinChallenge(user_id); // 이번달에 참여한 챌린지 가져오기
        List<GetPostRes> postRes = postRepository.findRecentPostByUserEmail(user_id);  // 가장 최근에 작성된 post 6개

        return GetProfileRes.builder()
                .profileImg(user.getProfileImg())
                .username(user.getUsername())
                .challengeSummary(getChallengeSummaryRes)
                .postRes(postRes).build();

    }

    @Transactional(readOnly = true)
    public ChallengeRes showChallengeList(String email, Long challenge_id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        boolean status = challengeUserRepository.existsByChallengeIdAndUserId(challenge_id, user.getId());  // 해당 챌린지에 참여하는지
        List<GetChallengeListRes> challengeList = challengePostRepository.findAllImageByChallengeId(challenge_id);  // 챌린지 참여 목록

        GetChallengeSummaryRes challengeSummary = challengeRepository.findById(challenge_id, GetChallengeSummaryRes.class); // 해당 챌린지 정보


        return ChallengeRes.builder()
                .status(status)
                .getChallengeLists(challengeList)
                .summaryRes(challengeSummary).build();

    }

    @Transactional(rollbackFor = Exception.class)
    public void numDeleted(List<Long> challengeId) {
        for (Long id : challengeId) {
            challengeRepository.findById(id).get().downNum();  // 참여 인원 -1
        }
    }

    @Transactional(readOnly = true)
    public ChallengeMain getMain(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        ChallengeTitle popularityChallenge = challengeRepository.findPopularityChallenge(); //사실상 참여 중인 챌린지가 없다면 전체 인기 챌린지랑 쿼리문이 동일하다.
        List<Long> myChallenges = challengeRepository.findParticipatingChallenge(user.getId()); //사용자고 참여하고 있는 챌린지 조회

        if (myChallenges == null) { //참여중인 챌린지가 없다면
            return ChallengeMain.builder()
                    .exist(false)
                    .participatingChallenge(challengeRepository.findPopularityChallenge())
                    .popularityChallenge(popularityChallenge)
                    .build();
        }

        return ChallengeMain.builder()
                .exist(true)
                .participatingChallenge(challengeRepository.findParticipatingPopularityChallenge(myChallenges))
                .popularityChallenge(popularityChallenge)
                .build();
    }

    @Transactional(readOnly = true)
    public ChallengeRes getPopularityChallenge(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        Long popularityChallengeId = challengeRepository.findPopularityChallengeId(); //가장 만은 참여 챌린지 조회
        Long challengeId = popularityChallengeId;
        if (popularityChallengeId == null) { //참여한 사람이 없을 경우 최근 챌린지 조회
            challengeId = challengeRepository.findCurrentChallengeId();
        }

        boolean status = challengeUserRepository.existsByChallengeIdAndUserId(challengeId, user.getId());  // 해당 챌린지에 참여하는지
        List<GetChallengeListRes> challengeList = challengePostRepository.findAllImageByChallengeId(challengeId);  // 챌린지 참여 목록

        GetChallengeSummaryRes challengeSummary = challengeRepository.findById(challengeId, GetChallengeSummaryRes.class); // 해당 챌린지 정보

        return ChallengeRes.builder()
                .status(status)
                .getChallengeLists(challengeList)
                .summaryRes(challengeSummary).build();
    }
}
