package itstime.shootit.greme.challenge.exception;

import itstime.shootit.greme.common.error.dto.ErrorMessage;
import itstime.shootit.greme.common.error.exception.BusinessException;

public class FailAddChallengeException extends BusinessException {

    public FailAddChallengeException(){
        super(ErrorMessage.FAIL_ADD_CHALLENGE);
    }
}
