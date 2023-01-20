package itstime.shootit.greme.user.application;

import itstime.shootit.greme.challenge.application.ChallengeService;
import itstime.shootit.greme.challenge.application.ChallengeUserService;
import itstime.shootit.greme.challenge.infrastructure.ChallengeRepository;
import itstime.shootit.greme.post.application.PostService;
import itstime.shootit.greme.user.domain.Interest;
import itstime.shootit.greme.user.domain.InterestType;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.InterestReq;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.dto.request.UserInfoReq;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.exception.UserAlreadyDeleted;
import itstime.shootit.greme.user.infrastructure.InterestRepository;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final ChallengeRepository challengeRepository;
    private final PostService postService;
    private final ChallengeUserService challengeUserService;
    private final ChallengeService challengeService;

    public void checkExistsUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ExistsUsernameException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpReq signUpReq) {
        try {
            userRepository.save(User.builder()
                    .email(signUpReq.getEmail())
                    .username(signUpReq.getUsername())
                    .activated(true)
                    .build());
        } catch (Exception e) {
            throw new FailSignUpException();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInterest(String email, InterestReq interestReq) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        interestRepository.deleteInterestsByUser(user); //사용자의 기존 관심사 삭제

        interestRepository.saveAll(interestReq.getInterestType() //모든 관심사 저장
                .stream()
                .map(interest -> Interest.builder()
                        .user(user)
                        .interestType(InterestType.fromValue(interest))
                        .build())
                .collect(Collectors.toList()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(String email, UserInfoReq userInfoReq) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        user.updateArea(userInfoReq.getArea());
        user.updateGender(userInfoReq.getGenderType());
        user.updatePurpose(userInfoReq.getPurpose());

        userRepository.save(user);
    }

    public User getUserInfo(String email) {
        return userRepository.findByEmail(email).orElseThrow(NotExistUserException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String email) {
        try {
            User user = getUserInfo(email).setActiveFalse();  // activated값 false로
            postService.deleteAllPosts(user);  // 해당 유저가 작성한 post 모두 삭제

            List<Long> allJoinId = challengeUserService.getAllJoinId(user.getId());  // 유저 id로 참여하고 있는 챌린지 id 모두 가져오기
//            challengeService.numDeleted(allJoinId);  // 해당 유저가 참여하는 챌린지 인원 -1
            challengeRepository.numDeleted(allJoinId);  // 쿼리문으로 챌린지 인원 1씩 감소
            log.info("email : {} 유저가 탈퇴했습니다.", user.getEmail());
        } catch (Exception ignored) {
            throw new UserAlreadyDeleted();
        }
    }

}
