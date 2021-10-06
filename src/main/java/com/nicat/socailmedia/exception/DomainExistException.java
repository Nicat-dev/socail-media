package com.nicat.socailmedia.exception;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class DomainExistException extends RuntimeException {
    public DomainExistException(
            @Email(message = "Email is not valid")
            @NotEmpty(message = "Email cannot be empty")
                    String message) {
        super(message);
    }
}
