//package itstime.shootit.greme.user.application;
//
//import itstime.shootit.greme.user.domain.User;
//import itstime.shootit.greme.user.infrastructure.UserRepository;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@SpringBootTest
//@DataJpaTest
//class UserServiceTest {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    @DisplayName("중복 닉네임이 없는 경우")
//    void isNotDuplicatedUsername() {
//        //given 준비
//        String username = "test";
//
//        //when 실행
//        boolean isDuplicationUsername = userRepository.existsByUsername(username);
//
//        //then 검증
//        assertEquals(false, isDuplicationUsername);
//    }
//
//    @Test
//    @DisplayName("중복 닉네임이 있는 경우")
//    void isDuplicatedUsername() {
//        //given 준비
//        String username = "test";
//        userRepository.save(User.builder()
//                .username(username)
//                .email("test")
//                .build());
//
//        //when & then
//        assertThrows(RuntimeException.class, () -> {
//            userService.checkExistsUsername(username);
//        });
//    }
//}