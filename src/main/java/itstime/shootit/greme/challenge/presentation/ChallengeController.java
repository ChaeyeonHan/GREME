package itstime.shootit.greme.challenge.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import itstime.shootit.greme.challenge.application.ChallengeService;
import itstime.shootit.greme.challenge.dto.ChallengeSummary;
import itstime.shootit.greme.oauth.application.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{userId}")
    public List<ChallengeSummary> challenge(@PathVariable Long userId){
        return challengeService.challenge(userId);
    }

    @GetMapping("/{userId}/join")
    public List<ChallengeSummary> joinChallenge(@PathVariable Long userId){
        return challengeService.joinChallenge(userId);
    }

    @Operation(summary = "챌린지 등록", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰"),
        @Parameter(name = "challengeId", description = "참여하려는 챌린지 id")}
    )
    @PostMapping("/join/{challengeId}")
    public void addChallenge(@PathVariable Long challengeId, @RequestHeader("accessToken") String accessToken){
        challengeService.addChallenge(jwtTokenProvider.getEmail(accessToken), challengeId);
    }


}
