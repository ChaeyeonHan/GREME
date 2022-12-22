package itstime.shootit.greme.user.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class FailSignUpException  extends BusinessException {
    public FailSignUpException() {
        super(ErrorMessage.FAIL_SIGN_UP);
    }
}
