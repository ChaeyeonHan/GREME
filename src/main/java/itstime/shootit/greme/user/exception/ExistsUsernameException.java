package itstime.shootit.greme.user.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class ExistsUsernameException extends BusinessException {
    public ExistsUsernameException() {
        super(ErrorMessage.EXISTS_USERNAME);
    }
}
