package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.infrastructure.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeUserService {

    private final ChallengeUserRepository challengeUserRepository;

    @Transactional(readOnly = true)
    public boolean existsByUserId(Long user_id) {
        return challengeUserRepository.existsByUserId(user_id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Long> getAllJoinId(Long userId) {  // 유저 id로 참여하고 있는 챌린지 id 모두 가져오기
        List<ChallengeUser> allByUserId = challengeUserRepository.findAllByUserId(userId);
        List<Long> joinId = new ArrayList<>();

        for (ChallengeUser challengeUser : allByUserId) {
            joinId.add(challengeUser.getId());
        }
        return joinId;
    }

    @Transactional
    public void deleteAllRecords(Long userId) {
        challengeUserRepository.deleteAllById(Collections.singleton(userId));
    }

}
