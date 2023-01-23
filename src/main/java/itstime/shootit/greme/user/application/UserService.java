package itstime.shootit.greme.user.application;

import itstime.shootit.greme.aws.application.S3Uploader;
import itstime.shootit.greme.challenge.application.ChallengeService;
import itstime.shootit.greme.challenge.application.ChallengeUserService;
import itstime.shootit.greme.challenge.dto.response.GetChallengeSummaryRes;
import itstime.shootit.greme.challenge.infrastructure.ChallengeRepository;
import itstime.shootit.greme.challenge.infrastructure.ChallengeUserRepository;
import itstime.shootit.greme.post.application.PostService;
import itstime.shootit.greme.user.domain.Interest;
import itstime.shootit.greme.user.domain.InterestType;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.*;
import itstime.shootit.greme.user.dto.response.ConfigurationRes;
import itstime.shootit.greme.user.dto.response.ProfileRes;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.exception.UserAlreadyDeleted;
import itstime.shootit.greme.user.infrastructure.InterestRepository;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    private final ChallengeUserRepository challengeUserRepository;
    private final ChallengeService challengeService;
    private final S3Uploader s3Uploader;

    @Value("${PROFILE_URL}")
    private String PROFILE_URL;


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
                    .profileImg(PROFILE_URL)
                    .username(signUpReq.getUsername())
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
            User user = userRepository.findByEmail(email)
                    .orElseThrow(NotExistUserException::new);
            postService.deleteAllPosts(user);  // 해당 유저가 작성한 post 모두 삭제

            if (challengeUserRepository.existsByUserId(user.getId())) {
                List<Long> allJoinId = challengeUserService.getAllJoinId(user.getId());  // 유저 id로 참여하고 있는 챌린지 id 모두 가져오기
//            challengeService.numDeleted(allJoinId);  // 해당 유저가 참여하는 챌린지 인원 -1
                challengeUserService.deleteAllRecords(user.getId());

                challengeRepository.numDeleted(allJoinId);  // 쿼리문으로 챌린지 인원 1씩 감소
            }

            interestRepository.deleteInterestsByUser(user);
            userRepository.delete(user);
            log.info("{} 유저가 탈퇴했습니다.", user.getEmail());

        } catch (Exception ignored) {
            throw new UserAlreadyDeleted();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProfile1(String email, Profile1Req profile1Req) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        user.updateGender(profile1Req.getGenderType());

        interestRepository.deleteInterestsByUser(user); //사용자의 기존 관심사 삭제
        interestRepository.saveAll(profile1Req.getInterestType() //모든 관심사 저장
                .stream()
                .map(interest -> Interest.builder()
                        .user(user)
                        .interestType(InterestType.fromValue(interest))
                        .build())
                .collect(Collectors.toList()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProfile2(String email, Profile2Req profile2Req) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        user.updatePurpose(profile2Req.getPurpose());
        user.updateArea(profile2Req.getArea());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProfileImage(String email, MultipartFile multipartFile) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        List<String> fileNames = s3Uploader.uploadFile(List.of(multipartFile));

        user.updateProfileImage(fileNames.get(0));
    }

    public ConfigurationRes findConfiguration(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        return ConfigurationRes.builder()
                .username(user.getUsername())
                .imageUrl(user.getProfileImg())
                .challengeJoinSummary(
                        challengeRepository.findMonthJoinChallenge(user.getId())
                                .orElseGet(ArrayList::new)
                )
                .build();
    }

    public ProfileRes findProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(NotExistUserException::new);

        List<Interest> interest = user.getInterest();

        List<Integer> interestType = interest.stream() //관심사 enum index로 저장
                .map(Interest::getInterestType)
                .mapToInt(Enum::ordinal)
                .boxed()
                .collect(Collectors.toList());

        return ProfileRes.builder()
                .username(user.getUsername())
                .imageUrl(user.getProfileImg())
                .interestType(interestType)
                .genderType(user.getGender().ordinal())
                .area(user.getArea())
                .purpose(user.getPurpose())
                .build();
    }
}
