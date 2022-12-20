package itstime.shootit.greme.common.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    EXISTS_USERNAME(HttpStatus.CONFLICT,"존재하는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}