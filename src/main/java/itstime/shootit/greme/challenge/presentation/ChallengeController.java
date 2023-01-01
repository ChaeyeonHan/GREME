package itstime.shootit.greme.challenge.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import itstime.shootit.greme.challenge.application.ChallengeService;
import itstime.shootit.greme.challenge.dto.GetChallengeSummaryRes;
import itstime.shootit.greme.challenge.dto.ChallengeTitle;
import itstime.shootit.greme.oauth.application.JwtTokenProvider;
import itstime.shootit.greme.user.dto.GetProfileRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{userId}")
    public List<GetChallengeSummaryRes> challenge(@PathVariable Long userId){
        return challengeService.challenge(userId);
    }

    @GetMapping("/{userId}/join")
    public List<GetChallengeSummaryRes> joinChallenge(@PathVariable Long userId){
        return challengeService.joinChallenge(userId);
    }

    @Operation(summary = "챌린지 등록", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰"),
        @Parameter(name = "challengeId", description = "참여하려는 챌린지 id")}
    )
    @PostMapping("/{challengeId}")
    public ResponseEntity<Void> addChallenge(@PathVariable Long challengeId, @RequestHeader("accessToken") String accessToken){
        challengeService.addChallenge(jwtTokenProvider.getEmail(accessToken), challengeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "챌린지 제외", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰"),
    @Parameter(name = "challengeId", description = "제외하려는 챌린지 id")})
    @PatchMapping("/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long challengeId, @RequestHeader("accessToken") String accessToken){
        challengeService.deleteChallenge(jwtTokenProvider.getEmail(accessToken), challengeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "다이어리에 사용할 사용자가 참여중인 챌린지 제목만 조회", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰")})
    @GetMapping("/participating/title")
    public List<ChallengeTitle> findParticipatingChallengeTitle(@RequestHeader("accessToken") String accessToken){
        return challengeService.findJoinChallengeTitle(jwtTokenProvider.getEmail(accessToken));
    }

    @Operation(summary = "다른 유저의 프로필 조회하기", parameters = {@Parameter(name = "accessToken", description = "액세스 토큰"),
        @Parameter(name = "user_id", description = "프로필 조회하려는 유저의 id값")})
    @GetMapping("/profile/{user_id}")
    public GetProfileRes showUserProfile(@PathVariable Long user_id, @RequestHeader("accessToken") String accessToken){
        return challengeService.showUserProfile(user_id);
    }

}
