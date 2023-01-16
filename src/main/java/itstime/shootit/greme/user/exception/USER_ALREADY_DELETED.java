package itstime.shootit.greme.user.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class USER_ALREADY_DELETED extends BusinessException {

    public USER_ALREADY_DELETED() {
        super(ErrorMessage.USER_ALREADY_DELETED);
    }
}
