package com.greenfoxacademy.ebayclone.exeptions;

import com.greenfoxacademy.ebayclone.dtos.MessageDTO;
import com.greenfoxacademy.ebayclone.exeptions.user.PasswordInvalidException;
import com.greenfoxacademy.ebayclone.exeptions.user.UsernameAlreadyInUseException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(
            UsernameNotFoundException usernameNotFoundException
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MessageDTO(usernameNotFoundException.getMessage())
        );
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<?> handlePasswordInvalidException(
            PasswordInvalidException passwordInvalidException
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new MessageDTO(passwordInvalidException.getMessage())
        );
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<?> handleUsernameAlreadyInUseException(
            UsernameAlreadyInUseException usernameAlreadyInUseException
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new MessageDTO(usernameAlreadyInUseException.getMessage())
        );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(
            ValidationException validationException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(validationException.getMessage().split("/:/"))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MessageDTO(illegalArgumentException.getMessage())
        );
    }
}
