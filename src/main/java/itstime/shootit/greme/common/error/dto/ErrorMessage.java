package itstime.shootit.greme.common.error.dto;

import itstime.shootit.greme.user.exception.FailSignUpException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    EXISTS_USERNAME(HttpStatus.CONFLICT,"존재하는 닉네임입니다."),
    FAIL_SIGN_UP(HttpStatus.UNPROCESSABLE_ENTITY, "회원가입에 실패하였습니다");

    private final HttpStatus httpStatus;
    private final String message;
}