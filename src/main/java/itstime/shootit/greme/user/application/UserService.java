package itstime.shootit.greme.user.application;

import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void checkExistsUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ExistsUsernameException();
        }
    }
}
