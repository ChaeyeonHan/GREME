package itstime.shootit.greme.user.application;

import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
