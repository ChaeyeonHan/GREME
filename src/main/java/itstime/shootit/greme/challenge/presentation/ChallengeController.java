package itstime.shootit.greme.challenge.presentation;

import itstime.shootit.greme.challenge.application.ChallengeService;
import itstime.shootit.greme.challenge.domain.Challenge;
import itstime.shootit.greme.challenge.dto.GetChallengeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/{userId}")
    public List<Challenge> challenge(@PathVariable Long userId){
        return challengeService.challenge(userId);
    }

    @GetMapping("/{userId}/join")
    public List<GetChallengeRes> joinChallenge(@PathVariable Long userId){
        return challengeService.joinChallenge(userId);
    }


}
