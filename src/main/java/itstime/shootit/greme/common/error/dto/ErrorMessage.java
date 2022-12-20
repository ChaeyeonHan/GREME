package itstime.shootit.greme.common.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    TEST(HttpStatus.NOT_FOUND,"TEST");

    private final HttpStatus httpStatus;
    private final String message;
}