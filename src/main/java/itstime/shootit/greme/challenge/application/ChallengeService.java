package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.ChallengUserRepository;
import itstime.shootit.greme.challenge.dto.ChallengeSummary;
import itstime.shootit.greme.user.exception.NotExistUserException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengUserRepository challengUserRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChallengeSummary> challenge(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new NotExistUserException();
        }

        return challengUserRepository.mfindChallenge(userId);
    }

    @Transactional(readOnly = true)
    public List<ChallengeSummary> joinChallenge(Long userId){
        if(!userRepository.existsById(userId)) {
            throw new NotExistUserException();
        }

        return challengUserRepository.mfindJoinChallenge(userId);
    }


}
