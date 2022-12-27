package itstime.shootit.greme.challenge.application;

import itstime.shootit.greme.challenge.ChallengeRepository;
import itstime.shootit.greme.challenge.ChallengeUserRepository;
import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.domain.ChallengeUser;
import itstime.shootit.greme.challenge.exception.FailAddChallengeException;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChallengeServiceTest {

    @Autowired
    private ChallengeUserRepository challengeUserRepository;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeRepository challengeRepository;


    @Test
    @DisplayName("챌린지 등록")
    void addChallenge() {
        //given 준비
        User user = User.builder()
                .username("username1")
                .email("test")
                .build();
        Challenge challenge = Challenge.builder()
                .title("테스트 챌린지1")
                .build();
        userRepository.save(user);
        challengeRepository.save(challenge);

        ChallengeUser challengeUser = ChallengeUser.builder()
                .user(user)
                .challenge(challenge)
                .build();

        // when 실행
        ChallengeUser save = challengeUserRepository.save(challengeUser);

        // then 결과확인
        Assertions.assertEquals(save.getId(), challengeUser.getId());
    }

    @Test
    @DisplayName("해당 챌린지가 이미 등록되어 있는 경우")
    void isDuplicateChallenge(){
        //given
        User user = User.builder()
                .username("username1")
                .email("test")
                .build();
        Challenge challenge = Challenge.builder()
                .title("테스트 챌린지1")
                .build();
        userRepository.save(user);
        challengeRepository.save(challenge);

        ChallengeUser challengeUser = ChallengeUser.builder()
                .user(user)
                .challenge(challenge)
                .build();

        //when
        ChallengeUser save = challengeUserRepository.save(challengeUser);

        //then
        Assertions.assertThrows(FailAddChallengeException.class, () ->{
            challengeService.addChallenge(user.getEmail(), challenge.getId());
        });
    }


}
