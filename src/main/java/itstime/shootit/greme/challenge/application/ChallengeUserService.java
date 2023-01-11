package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.infrastructure.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeUserService {

    private final ChallengeUserRepository challengeUserRepository;

    @Transactional(readOnly = true)
    public boolean existsByUserId(Long user_id) {
        return challengeUserRepository.existsByUserId(user_id);
    }
}
