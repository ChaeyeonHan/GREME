package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.ChallengUserRepository;
import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.dto.GetChallengeRes;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengUserRepository challengUserRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<GetChallengeRes> challenge(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
        return challengUserRepository.mfindChallenge(userId).stream()
                .map(GetChallengeRes::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetChallengeRes> joinChallenge(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        return challengUserRepository.mfindJoinChallenge(userId).stream()
                .map(GetChallengeRes::new)
                .collect(Collectors.toList());
    }


}
