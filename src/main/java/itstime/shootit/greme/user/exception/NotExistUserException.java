package itstime.shootit.greme.user.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class NotExistUserException extends BusinessException {
    public NotExistUserException() {
        super(ErrorMessage.NOT_EXIST_USER);
    }
}
