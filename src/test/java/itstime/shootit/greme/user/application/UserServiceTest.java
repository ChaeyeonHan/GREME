package itstime.shootit.greme.user.application;

import itstime.shootit.greme.user.Gender;
import itstime.shootit.greme.user.domain.Interest;
import itstime.shootit.greme.user.domain.InterestType;
import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.InterestReq;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.dto.request.UserInfoReq;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.infrastructure.InterestRepository;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private InterestRepository interestRepository;

    @Test
    @DisplayName("중복 닉네임이 없는 경우")
    void isNotDuplicatedUsername() {
        //given 준비
        String username = "test";

        //when 실행
        boolean isDuplicationUsername = userRepository.existsByUsername(username);

        //then 검증
        assertEquals(false, isDuplicationUsername);
    }

    @Test
    @DisplayName("중복 닉네임이 있는 경우")
    void isDuplicatedUsername() {
        //given
        String username = "test";
        User user = User.builder()
                .username(username)
                .email("test")
                .build();
        userRepository.save(user);

        //when & then
        assertThrows(ExistsUsernameException.class, () -> {
            userService.checkExistsUsername(username);
        });
    }

    @Test
    @DisplayName("중복 닉네임으로 인한 회원 가입 실패")
    void failSignUpByDuplicationUsername() {
        //given
        User user = User.builder()
                .username("test")
                .email("email")
                .build();
        userRepository.save(user);

        //when & then
        assertThrows(FailSignUpException.class, () -> {
            userService.signUp(new SignUpReq("email2", "test"));
        });
    }

    @Test
    @DisplayName("관심사 등록")
    void registInterests() {
        //given
        User user = User.builder()
                .username("test")
                .email("email")
                .build();
        userRepository.save(user);
        List<Integer> interests = List.of(0, 1, 2);
        List<InterestType> expected = interests.stream()
                .map(InterestType::fromValue)
                .collect(Collectors.toList());

        //when
        userService.updateInterest("email", new InterestReq(interests));
        List<InterestType> result = interestRepository.findAllByUser(user)
                .stream()
                .map(Interest::getInterestType)
                .collect(Collectors.toList());

        //then
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    @DisplayName("추가 정보 변경")
    void updateUserInfo() {
        //given
        User user = User.builder()
                .username("test")
                .email("email")
                .build();
        userRepository.save(user);
        String area = "서울특별시 강남구";
        Integer genderType = 0;
        String purpose = "몰라요";

        //when
        userService.updateInfo("email", new UserInfoReq(genderType, area, purpose));
        User result = userRepository.findByEmail("email").get();

        //then
        assertEquals(true, area.equals(result.getArea())
                && Gender.MALE.equals(result.getGender())
                && purpose.equals(result.getPurpose()));
    }
}