package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.infrastructure.ChallengePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengePostService {

    private final ChallengePostRepository challengePostRepository;

    @Transactional(readOnly = true)
    public boolean recordExist(Long user_id) {
        return challengePostRepository.existsByuserId(user_id);
    }
}
