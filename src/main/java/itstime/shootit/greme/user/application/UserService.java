package itstime.shootit.greme.user.application;

import itstime.shootit.greme.user.domain.Interest;
import itstime.shootit.greme.user.domain.InterestType;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.InterestReq;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.dto.request.UserInfoReq;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.infrastructure.InterestRepository;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

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


}
