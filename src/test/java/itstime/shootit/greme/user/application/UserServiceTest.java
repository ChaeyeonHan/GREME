package itstime.shootit.greme.user.application;

import itstime.shootit.greme.user.domain.User;
import itstime.shootit.greme.user.dto.request.SignUpReq;
import itstime.shootit.greme.user.exception.ExistsUsernameException;
import itstime.shootit.greme.user.exception.FailSignUpException;
import itstime.shootit.greme.user.infrastructure.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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
            userService.signUp(new SignUpReq("email2","test"));
        });
    }

}