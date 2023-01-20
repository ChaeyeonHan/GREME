package itstime.shootit.greme.user.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class UserAlreadyDeleted extends BusinessException {

    public UserAlreadyDeleted() {
        super(ErrorMessage.USER_ALREADY_DELETED);
    }
}
