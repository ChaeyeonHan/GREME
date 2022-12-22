package itstime.shootit.greme.common.error.handler;

import itstime.shootit.greme.common.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //비즈니스 로직 예외 핸들링
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(final BusinessException e) {
        return ResponseEntity
                .status(e.getErrorMessage().getHttpStatus())
                .body(e.getErrorMessage().getMessage());
    }

    //전역 예외 핸들링
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(
            final Exception e,
            final HttpServletRequest request
    ) {
        System.out.println(request.getRequestURI()+": " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
