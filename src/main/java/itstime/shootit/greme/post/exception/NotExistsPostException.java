package itstime.shootit.greme.post.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class NotExistsPostException extends BusinessException {
    public NotExistsPostException() {
        super(ErrorMessage.NOT_EXISTS_POST);
    }
}
