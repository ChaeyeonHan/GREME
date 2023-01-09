package itstime.shootit.greme.common.error.dto;

import itstime.shootit.greme.user.exception.FailSignUpException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    EXISTS_USERNAME(HttpStatus.CONFLICT,"존재하는 닉네임입니다."),
    FAIL_SIGN_UP(HttpStatus.UNPROCESSABLE_ENTITY, "회원가입에 실패하였습니다"),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    FAIL_ADD_CHALLENGE(HttpStatus.UNPROCESSABLE_ENTITY, "챌린지 등록에 실패하였습니다."),
    NOT_EXIST_POST(HttpStatus.NOT_FOUND,"존재하지 않는 다이어리입니다.");


    private final HttpStatus httpStatus;
    private final String message;
}